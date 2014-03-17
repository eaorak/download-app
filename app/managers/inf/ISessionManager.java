package managers.inf;

import managers.SessionManager.Result;
import models.cons.Session;

public interface ISessionManager {

	Session session(String sessionId);

	Session login(String login, String pass);

	void logout(String sessionId);

	Result valid(String sessionId, String url);

}
