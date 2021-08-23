package org.waterproofingdata.wpdauth.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.waterproofingdata.wpdauth.exception.CustomException;
import org.waterproofingdata.wpdauth.model.ForgotPasswordKeys;
import org.waterproofingdata.wpdauth.model.Users;
import org.waterproofingdata.wpdauth.repository.ForgotPasswordKeysRepository;
import org.waterproofingdata.wpdauth.repository.UsersRepository;
import org.waterproofingdata.wpdauth.security.JwtTokenProvider;

@Service
public class ForgotPasswordService {
	@Autowired
	private ForgotPasswordKeysRepository forgotPasswordKeysRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private JavaMailSender mailSender;	
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
	public void sendkey(String email) {
		Users user = usersRepository.findByEmail(email);
	    if (user == null) {
		      throw new CustomException("The user email doesn't exist", HttpStatus.NOT_FOUND);
	    }		
		
		Random rand = new Random();
		String key = String.format("%04d", rand.nextInt(10000));
		//System.out.printf("%04d%n", rand.nextInt(10000));
		
		ForgotPasswordKeys entity = new ForgotPasswordKeys();
		entity.setEmail(email);
		entity.setKey(key);
		forgotPasswordKeysRepository.save(entity);
		
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("noreply@wp6.com");
        message.setTo(email); 
        message.setSubject("Envio de código para alteração de senha"); 
        message.setText(String.format("Olá! Segue o código para alterar sua senha:'%s'. Informe esse código no aplicativo para prosseguir.", key));
        mailSender.send(message);		
	}
	
	public String loginByEmailAndKey(String email, String key) {
		ForgotPasswordKeys entity = forgotPasswordKeysRepository.findTodayRecordByEmailANDKey(email, key);
		if (entity == null) {
			throw new CustomException("The email and forgot key don't exist", HttpStatus.NOT_FOUND);
		}
		
		Users user = usersRepository.findByEmail(email);
	    if (user == null) {
		      throw new CustomException("The user email doesn't exist", HttpStatus.NOT_FOUND);
	    }		
		
	    String username = user.getUsername();
		return jwtTokenProvider.createToken(username, usersRepository.findByUsername(username).getRoles());
	}
	
	//public String loginByEmailAndAnswers(String email, List<PasswordAnswers> passwordAnswers)
	
	public void passwordUpdate(String email, String newPassword) {
		Users user = usersRepository.findByEmail(email);
	    if (user == null) {
		      throw new CustomException("The user email doesn't exist", HttpStatus.NOT_FOUND);
	    }
	    user.setPassword(passwordEncoder.encode(newPassword));
	    usersRepository.save(user);
	}
}
