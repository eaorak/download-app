package managers;

import java.util.concurrent.TimeUnit;

import models.User;
import models.cons.Session;
import play.Logger;
import play.libs.Crypto;
import play.libs.Crypto.HashType;
import utils.Util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author ender
 */
public class SessionManager extends BaseManager implements RemovalListener<String, Session> {

	private final int ACCESS = 10; // In minutes
	private final int LIFETIME = 3; // In hours
	private final String LIFETIME_PLAY = LIFETIME + "h";
	//
	private Cache<String, Session> sessions;
	private PersistenceManager pem;

	public SessionManager() {
	}

	@Override
	public void initialize() {
		pem = M.get(PersistenceManager.class);
		sessions = CacheBuilder.newBuilder() //
				.concurrencyLevel(4)//
				.maximumSize(10000)//
				.removalListener(this)//
				.expireAfterAccess(ACCESS, TimeUnit.MINUTES)//
				.expireAfterWrite(LIFETIME, TimeUnit.HOURS)//
				.build();
	}

	public Session session(String sessionId) {
		return sessions.getIfPresent(sessionId);
	}

	public Session login(String login, String pass) {
		User user = pem.findFirst(User.class, "login=?", login);
		Session session = null;
		if (user != null) {
			String hashed = Crypto.passwordHash(pass, HashType.MD5);
			if (user.getPass().equals(hashed)) {
				String sessionId = Util.uuid();
				session = new Session(user, sessionId, LIFETIME_PLAY);
				sessions.put(sessionId, session);
				Logger.info("[SessionManager.login] New session created [%s].", session);
				return session;
			}
		}
		throw new RuntimeException("Invalid login or password !");
	}

	public void logout(String sessionId) {
		sessions.invalidate(sessionId);
		Logger.info("[SessionManager.logout] Session with id [%s] has removed.", sessionId);
	}

	public static enum Result {
		VALID, INVALID, UNAUTHORIZED;
	}

	public Result valid(String sessionId, String url) {
		Session session = session(sessionId);
		if (session == null) {
			return Result.INVALID;
		}
		if (!session.getUser().getRole().allowed(url)) {
			return Result.UNAUTHORIZED;
		}
		return Result.VALID;
	}

	@Override
	public void onRemoval(RemovalNotification<String, Session> removal) {
		Logger.info("[SessionManager.onRemoval] Session [%s] has expired and removed.", removal.getValue());
	}

}
