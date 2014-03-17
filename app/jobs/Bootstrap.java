package jobs;

import java.util.List;

import managers.M;
import managers.PersistenceManager;
import managers.inf.IPersistenceManager;
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

	@Override
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
		IPersistenceManager pem = M.get(PersistenceManager.class);
		// Test data
		if (pem.find(User.class, "").size() == 0) {
			Fixtures.loadModels("initial-data.yml");
			User user = pem.findFirst(User.class, "login=?", "user@sahibinden.com");
			//
			File file = new File("JavaSwing.pdf",
					"http://www.baskent.edu.tr/~tkaracay/etudio/ders/prg/java/ch27/JavaSwing.pdf", "application/pdf");
			user.addFile(file);
			//
			file = new File("ScalaTutorial.pdf", "http://www.scala-lang.org/docu/files/ScalaTutorial.pdf",
					"application/pdf");
			user.addFile(file);
			//
			file = new File("Cat wallpaper", "http://miriadna.com/desctopwalls/images/max/Green-cat-eyes.jpg",
					"image/jpeg");
			user.addFile(file);
			//
			file = new File("Java ppt", "http://www.cs.virginia.edu/~evans/cs205/classes/class4/lecture4.ppt",
					"application/vnd.ms-powerpoint");
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
