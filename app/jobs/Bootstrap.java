package jobs;

import java.util.List;

import managers.M;
import managers.PersistenceManager;
import models.BaseModel;
import models.File;
import models.User;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * @author ender
 */
@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		initManagers();
		initData();
	}

	private void initManagers() {
		M.instance().initialize();
	}

	private void initData() {
		deleteAll();
		//
		PersistenceManager pem = M.get(PersistenceManager.class);
		if (pem.find(User.class, "").size() == 0) {
			Fixtures.loadModels("initial-data.yml");
			User user = pem.findFirst(User.class, "login=?", "eaorak@gmail.com");
			//
			File file = new File("Annox.pdf", "http://elron.co/files/annox.pdf", "application/pdf");
			user.addFile(file);
			//
			file = new File("Annox1.pdf", "http://elron.co/files/annox.pdf", "application/pdf");
			user.addFile(file);
			//
			file = new File("Annox2.pdf", "http://elron.co/files/annox.pdf", "application/pdf");
			user.addFile(file);
			//
			pem.save(user);
		}
	}

	private void deleteAll() {
		List<Class> cls = Play.classloader.getAssignableClasses(BaseModel.class);
		Fixtures.delete(cls.toArray(new Class[cls.size()]));
	}

}
