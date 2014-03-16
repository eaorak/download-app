package controllers;

import managers.M;
import managers.SessionManager;
import models.cons.Session;
import play.Logger;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Http.Cookie;

/**
 * @author ender
 */
public class BaseController extends Controller {

	public static final String COOKIE = "downapp";
	public static SessionManager ssm = M.get(SessionManager.class);

	@Before(priority = 1, unless = { "LoginController.login", "LoginController.authenticate" })
	public static void authorize() {
		String sessionId = sessionId();
		if (sessionId != null) {
			boolean valid = ssm.valid(sessionId, false);
			if (valid) {
				return;
			}
		}
		renderTemplate("LoginController/login.html");
	}

	@Catch(value = { Throwable.class })
	protected static void catchException(Throwable e) {
		Logger.error(e, "[BaseController.catch] Error occured.");
		error(e.getMessage());
	}

	protected static String sessionId() {
		Cookie cookie = request.cookies.get(COOKIE);
		return cookie == null ? null : cookie.value;
	}

	protected static Session session() {
		Cookie cookie = request.cookies.get(COOKIE);
		String sessionId = (cookie == null) ? null : cookie.value;
		return (sessionId == null) ? null : ssm.session(sessionId);
	}

}
