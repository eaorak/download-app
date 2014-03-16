package controllers;

import java.util.HashMap;
import java.util.Map;

import models.User;
import models.cons.Role;

import org.junit.Test;

import play.libs.Crypto;
import play.libs.Crypto.HashType;
import play.mvc.Http.Response;
import base.BaseTest;

public class LoginControllerTest extends BaseTest {

	@Test
	public void testLogin() {
		String pass = Crypto.passwordHash("123", HashType.MD5);
		User user = new User("eaorak@gmail.com", "Ender Aydin Orak", pass, Role.USER);
		em.persist(user);
		Map<String, String> params = new HashMap<String, String>();
		params.put("login", user.getLogin());
		params.put("password", user.getPass());
		Response post = POST("/login", params);
		String sessionId = post.getHeader("sessionId");
		assertNotNull(sessionId);
	}

}
