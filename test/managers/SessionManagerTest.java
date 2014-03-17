package managers;

import static org.mockito.Mockito.mock;
import models.User;
import models.cons.Role;
import models.cons.Session;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import play.libs.Crypto;
import play.libs.Crypto.HashType;
import play.test.UnitTest;

public class SessionManagerTest extends UnitTest {

	private PersistenceManager pem;
	private SessionManager ssm;
	String pass = "123";
	private User user;

	@Before
	public void generate() {
		pem = mock(PersistenceManager.class);
		M.instance().inject(PersistenceManager.class, pem);

		ssm = M.get(SessionManager.class);
		ssm.initialize();
		String hashed = Crypto.passwordHash(pass, HashType.MD5);
		user = new User("a@a.com", "Ali", hashed, Role.USER);
	}

	@Test(expected = RuntimeException.class)
	public void testNonExistingUserCanNotLogin() {
		ssm.login(user.getLogin(), pass);
	}

	@Test(expected = RuntimeException.class)
	public void testPasswordsCheckedAsHashed() {
		user.setPass(pass);
		Mockito.when(pem.findFirst(User.class, "login=?", user.getLogin())).thenReturn(user);
		ssm.login(user.getLogin(), pass);
	}

	@Test
	public void testExistingUserCanLogin() {
		Mockito.when(pem.findFirst(User.class, "login=?", user.getLogin())).thenReturn(user);
		Session session = ssm.login(user.getLogin(), pass);
		assertNotNull(session);
	}

	@Test
	public void testLogoutUsersSessionRemoved() {
		Mockito.when(pem.findFirst(User.class, "login=?", user.getLogin())).thenReturn(user);
		Session session = ssm.login(user.getLogin(), pass);
		ssm.logout(session.getSessionId());
		session = ssm.session(session.getSessionId());
		assertNull(session);
	}
}
