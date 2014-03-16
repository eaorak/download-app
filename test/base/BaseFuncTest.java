package base;

import java.util.List;

import managers.M;
import managers.PersistenceManager;
import models.BaseModel;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.Timeout;

import play.Play;
import play.test.Fixtures;
import play.test.FunctionalTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Ignore
public class BaseFuncTest extends FunctionalTest {

	protected static final String JSON = "application/json";
	protected static Gson gson = new GsonBuilder().create();
	protected static PersistenceManager pm;

	@Rule
	public Timeout globalTimeout = new Timeout(Integer.MAX_VALUE);

	@BeforeClass
	public static void loadData() {
		List<Class> cls = Play.classloader.getAssignableClasses(BaseModel.class);
		Fixtures.delete(cls.toArray(new Class[cls.size()]));
		Fixtures.loadModels("initial-data.yml");
		pm = M.get(PersistenceManager.class);
	}

}
