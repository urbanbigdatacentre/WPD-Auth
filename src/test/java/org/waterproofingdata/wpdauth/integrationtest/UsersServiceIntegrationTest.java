package org.waterproofingdata.wpdauth.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.waterproofingdata.wpdauth.exception.CustomException;
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
}
