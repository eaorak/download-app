package controllers;

import managers.M;
import managers.SessionManager;
import models.cons.Session;
import play.data.validation.Required;

/**
 * @author ender
 */
public class LoginController extends BaseController {

	private static SessionManager ssm = M.get(SessionManager.class);

	public static void login() {
		render();
	}

	public static void logout() {
		String sessionId = sessionId();
		ssm.logout(sessionId);
		login();
	}

	public static void authenticate(@Required String login, @Required String password) {
		Session session = ssm.login(login, password);
		if (session != null) {
			cookie(session);
			Application.index();
		}
		login();
	}

	private static void cookie(Session session) {
		response.setCookie(COOKIE, session.getSessionId(), session.getDuration());
	}

}
