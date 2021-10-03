package org.waterproofingdata.wpdauth.integrationtest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID; 

import com.google.gson.Gson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.waterproofingdata.wpdauth.exception.CustomException;
import org.waterproofingdata.wpdauth.model.Roles;
import org.waterproofingdata.wpdauth.model.Users;
import org.waterproofingdata.wpdauth.service.UsersService;

@SpringBootTest
public class UsersServiceIntegrationTest {
	@Autowired
	private UsersService usersService;
	
	@Test
	public void testInvalidLogin() {
		CustomException thrown = assertThrows(
				CustomException.class,
			() -> usersService.login("xpto", "xpto"),
			"Expected usersService.login(xpto, xpto) to throw, but it didn't"
		);
		
		assertTrue(thrown.getMessage().contains("Invalid username/password supplied"));
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, thrown.getHttpStatus());
	}
	
	@Test
	public void testAdmUserLogin() {
		String login = usersService.login("admin", "admin");
		assertNotNull(login, "Login token returned from usersService.login() should not be null");
	}
	
	@Test 
	public void testRandomUserSignup() {
		Users u = new Users();
		String uName = String.format("user%s", UUID.randomUUID().toString());
		u.setUsername(uName);
		u.setNickname(uName);		
		u.setPassword(UUID.randomUUID().toString());
		u.setState("SP");
		u.setCity("São Paulo");
		u.setTermsofusage(true);
		u.setRoles(new ArrayList<Roles>(Arrays.asList(Roles.ROLE_CLIENT)));
		String uJson = new Gson().toJson(u);
		
		String signup = usersService.signup(u);
		assertNotNull(signup, "Signup token returned from usersService.signup(user) should not be null");
	}
}
