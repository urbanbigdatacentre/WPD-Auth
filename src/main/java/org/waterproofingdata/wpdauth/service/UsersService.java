package org.waterproofingdata.wpdauth.service;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.waterproofingdata.wpdauth.exception.CustomException;
import org.waterproofingdata.wpdauth.model.EduCemadenOrganizations;
import org.waterproofingdata.wpdauth.model.Users;
import org.waterproofingdata.wpdauth.model.Roles;
import org.waterproofingdata.wpdauth.model.UsersEducemadenOrganizations;
import org.waterproofingdata.wpdauth.model.UsersProviderActivationKey;
import org.waterproofingdata.wpdauth.repository.EduCemadenOrganizationsRepository;
import org.waterproofingdata.wpdauth.repository.UsersEducemadenOrganizationsRepository;
import org.waterproofingdata.wpdauth.repository.UsersRepository;
import org.waterproofingdata.wpdauth.repository.UsersProviderActivationKeyRepository;
import org.waterproofingdata.wpdauth.security.JwtTokenProvider;

@Service
public class UsersService {
	  @Autowired
	  private UsersRepository usersRepository;
	  
	  @Autowired
	  private EduCemadenOrganizationsRepository eduCemadenOrganizationsRepository;
	  
	  @Autowired
	  private UsersEducemadenOrganizationsRepository usersEducemadenOrganizationsRepository;
	  
	  @Autowired
	  private UsersProviderActivationKeyRepository usersProviderActivationKeyRepository;

	  @Autowired
	  private PasswordEncoder passwordEncoder;

	  @Autowired
	  private JwtTokenProvider jwtTokenProvider;

	  @Autowired
	  private AuthenticationManager authenticationManager;
	  
	  @Autowired
	  private JavaMailSender mailSender;	
	  
	  public boolean existsByUsername(String username) {
		  return usersRepository.existsByUsername(username);
	  }
	  
	  public Users search(String username) {
		  Users user = usersRepository.findByUsername(username);
		  if (user == null) {
			  throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
		  }
		  return user;
	  }

	  public String login(String username, String password) {
	    try {
	    	Users u = search(username);
	    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	    	return jwtTokenProvider.createToken(username, u.getRoles());
	    } 
	    catch (CustomException ce) {
	    	throw ce;
	    }
	    catch (AuthenticationException ae) {
	    	throw new CustomException("Invalid username/password supplied", HttpStatus.NOT_FOUND);
	    }
	    catch (Exception e) {
	    	throw new CustomException("Something went wrong", HttpStatus.BAD_REQUEST);
	    }
	  }

	  public String signup(Users user) {
		  if (user.getUsername().length() == 0) {
			  throw new CustomException("Username must be provided", HttpStatus.UNPROCESSABLE_ENTITY);
		  }
			  
		  if (!existsByUsername(user.getUsername())) {
			  if (user.getNickname().length() == 0) {
				  throw new CustomException("Nickname must be provided", HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  else if (usersRepository.existsByNickname(user.getNickname())) {
				  String nickname_unique = usersRepository.findSuggestedNickname(user.getNickname());
				  throw new CustomException(String.format("Nickname already exists. Would you like to use '%s'?", nickname_unique), HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  if (user.getPassword().length() == 0) {
				  throw new CustomException("Password must be provided", HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  if (user.getState().length() == 0) {
				  throw new CustomException("State must be provided", HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  if (user.getCity().length() == 0) {
				  throw new CustomException("City must be provided", HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  if (user.getTermsofusage() != true) {
				  throw new CustomException("Terms of usage must be accepted", HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  if (user.getRoles().size() != 1) {
				  throw new CustomException("Only one user.role must be provided", HttpStatus.UNPROCESSABLE_ENTITY);				  
			  }

			  user.setPassword(passwordEncoder.encode(user.getPassword()));
			  user.setActive(0);
			  try {
				  usersRepository.save(user);
				  return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
			  }
			  catch (Exception e) {
				  throw new CustomException("Something went wrong", HttpStatus.BAD_REQUEST);
			  }
		  } 
		  else {
			  throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		  }
	  }
	  
	  public void sendAdminKeyByEmailCemaden(String emailcemaden, String username) {
	    Users user = search(username);
	    EduCemadenOrganizations eduCemadenOrganization = eduCemadenOrganizationsRepository.findByEmail(emailcemaden);
		if (eduCemadenOrganization == null) {
			throw new CustomException("Email Cemaden not found.", HttpStatus.NOT_FOUND);
		}
		
		String uuid = UUID.randomUUID().toString();
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@wp6.com");
        message.setTo(emailcemaden); 
        message.setSubject("Envio de código para alteração de senha"); 
        message.setText(String.format("Olá! O usuário '%s' solicitou a ativação dele para ADMIN dessa Instituição, por isso você está recebendo esse código: '%s'. Se estiver correto, informe esse código ao solicitante e peça para entrar no aplicativo para prosseguir.", user.getNickname(), uuid));
        try {
        	mailSender.send(message);
        }
        catch (MailException me) {
        	throw new CustomException("Something went wrong", HttpStatus.BAD_REQUEST);
        }
        
        UsersEducemadenOrganizations userEducemadenOrg = new UsersEducemadenOrganizations();
        userEducemadenOrg.setUsersid(user.getId());
        userEducemadenOrg.setEducemadenorganizationsid(eduCemadenOrganization.getId());
        userEducemadenOrg.setActivationkey(uuid);
        userEducemadenOrg.setActive(0);
        usersEducemadenOrganizationsRepository.save(userEducemadenOrg);
	  }
	  
	  public void activate(String username, String activationkey) {
		  Users user = search(username);
		  if (user.getActive() != 0) {
			  throw new CustomException("User should be inactive to be activated", HttpStatus.UNPROCESSABLE_ENTITY);
		  }
		  if (user.getRoles().size() != 1) {
			  throw new CustomException("User must have only one user.role", HttpStatus.UNPROCESSABLE_ENTITY);				  
		  }
		  
		  if (user.getRoles().get(0) == Roles.ROLE_INSTITUTION) {
			  UsersEducemadenOrganizations userEducemadenOrganization = usersEducemadenOrganizationsRepository.findByActivationkey(activationkey); 
			  if (userEducemadenOrganization == null) {
				  throw new CustomException("ROLE_INSTITUTION Activationkey not found.", HttpStatus.NOT_FOUND);				  
			  }
			  else if (user.getId() != userEducemadenOrganization.getUsersid()) {
				  throw new CustomException("Activationkey does not belong to the informed user", HttpStatus.UNPROCESSABLE_ENTITY);
			  }
			  
			  userEducemadenOrganization.setActive(1);
			  usersEducemadenOrganizationsRepository.save(userEducemadenOrganization);
			  
			  usersRepository.activateByUsername(username, 1);
			  
			  String uuid = UUID.randomUUID().toString();
			  UsersProviderActivationKey userRolesProviderActivationKey = new UsersProviderActivationKey();
			  userRolesProviderActivationKey.setUsersid(user.getId());
			  userRolesProviderActivationKey.setActivationkey(uuid);
			  usersProviderActivationKeyRepository.save(userRolesProviderActivationKey);
		  }
		  else if (user.getRoles().get(0) == Roles.ROLE_CLIENT) {
			  UsersProviderActivationKey userAdmProviderActivationKey = usersProviderActivationKeyRepository.findByActivationkey(activationkey);
			  if (userAdmProviderActivationKey == null) {
				  throw new CustomException(String.format("Activationkey '%s' not found.", activationkey), HttpStatus.NOT_FOUND);
			  }
			  
			  UsersEducemadenOrganizations userAdmEducemadenOrganization = usersEducemadenOrganizationsRepository.findByUserIdAndActivated(userAdmProviderActivationKey.getUsersid());
			  if (userAdmEducemadenOrganization == null) {
				  throw new CustomException("ROLE_INSTITUTION EduCemadenOrganization not found.", HttpStatus.NOT_FOUND);
			  }
			  
			  usersRepository.activateByUsername(username, 1);
			  
			  UsersEducemadenOrganizations userEducemadenOrg = new UsersEducemadenOrganizations();
			  userEducemadenOrg.setUsersid(user.getId());
			  userEducemadenOrg.setEducemadenorganizationsid(userAdmEducemadenOrganization.getEducemadenorganizationsid());
			  userEducemadenOrg.setActivationkey(activationkey);
			  userEducemadenOrg.setActive(1);
			  usersEducemadenOrganizationsRepository.save(userEducemadenOrg);
		  }
		  else if (user.getRoles().get(0) == Roles.ROLE_ADMIN) {
			  throw new CustomException("Admin users should be activated through database.", HttpStatus.UNPROCESSABLE_ENTITY);
		  }
		  else {
			  throw new CustomException("There is a problem with this User registration and it can not be activated.", HttpStatus.BAD_REQUEST); 
		  }
	  }

	  public Users whoami(HttpServletRequest req) {
	    return usersRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	  }
	  
	  public EduCemadenOrganizations findEduCemadenOrganizationById(Integer userid) {
		  UsersEducemadenOrganizations userAdmEducemadenOrganization = usersEducemadenOrganizationsRepository.findByUserIdAndActivated(userid);
		  if (userAdmEducemadenOrganization == null) {
			  return null;
		  }
		  return eduCemadenOrganizationsRepository.getById(userAdmEducemadenOrganization.getEducemadenorganizationsid());
	  }
	  
	  public UsersProviderActivationKey findProviderActivationKeyById(Integer usersid) {
	    return usersProviderActivationKeyRepository.findByUsersid(usersid);
	  }

	  public String refresh(String username) {
	    return jwtTokenProvider.createToken(username, usersRepository.findByUsername(username).getRoles());
	  }
}
