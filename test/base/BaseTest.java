package base;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
import org.junit.Ignore;

import play.db.jpa.JPA;
import play.db.jpa.JPAPlugin;
import play.test.FunctionalTest;

@Ignore
public class BaseTest extends FunctionalTest {

	protected static EntityManager em;

	@BeforeClass
	public static void loadData() {
		em = JPA.em();
		JPAPlugin.startTx(false);
	}

}
