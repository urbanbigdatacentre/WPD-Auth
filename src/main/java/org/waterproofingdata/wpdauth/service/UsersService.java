package org.waterproofingdata.wpdauth.service;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.waterproofingdata.wpdauth.exception.CustomException;
import org.waterproofingdata.wpdauth.model.EduCemadenOrganizations;
import org.waterproofingdata.wpdauth.model.Users;
import org.waterproofingdata.wpdauth.model.UsersRolesproviderActivationKey;
import org.waterproofingdata.wpdauth.repository.EduCemadenOrganizationsRepository;
import org.waterproofingdata.wpdauth.repository.UsersRepository;
import org.waterproofingdata.wpdauth.repository.UsersRolesproviderActivationKeyRepository;
import org.waterproofingdata.wpdauth.security.JwtTokenProvider;

@Service
public class UsersService {
	  @Autowired
	  private UsersRepository userRepository;
	  
	  @Autowired
	  private EduCemadenOrganizationsRepository eduCemadenOrganizationsRepository;
	  
	  @Autowired
	  private UsersRolesproviderActivationKeyRepository usersRolesproviderActivationKeyRepository;

	  @Autowired
	  private PasswordEncoder passwordEncoder;

	  @Autowired
	  private JwtTokenProvider jwtTokenProvider;

	  @Autowired
	  private AuthenticationManager authenticationManager;
	  
	  public boolean existsByUsername(String username) {
		  return userRepository.existsByUsername(username);
	  }

	  public String login(String username, String password) {
	    try {
	      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	    } 
	    catch (AuthenticationException e) {
	      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
	  }

	  public String signup(Users user) {
	    if (!existsByUsername(user.getUsername())) {
	      user.setPassword(passwordEncoder.encode(user.getPassword()));
	      user.setActive(0);
	      userRepository.save(user);
	      return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
	    } 
	    else {
	      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
	    }
	  }

	  public void activate(String username, String activationkey) {
		  String uuid = UUID.randomUUID().toString();
	    //userRepository.activateByUsername(username);
	  }

	  public Users search(String username) {
	    Users user = userRepository.findByUsername(username);
	    if (user == null) {
	      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
	    }
	    return user;
	  }

	  public Users whoami(HttpServletRequest req) {
	    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	  }
	  
	  public EduCemadenOrganizations findEduCemadenOrganizationById(Integer userid) {
	  	return eduCemadenOrganizationsRepository.findByUserId(userid); 
	  }
	  
	  public List<UsersRolesproviderActivationKey> findRolesproviderActivationKeysById(Integer usersid) {
	    return usersRolesproviderActivationKeyRepository.findByUsersid(usersid);
	  }

	  public String refresh(String username) {
	    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
	  }
}
