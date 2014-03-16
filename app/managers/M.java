package managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.Play;

/**
 * Manager controller
 * 
 * @author ender
 */
public class M {

	private static M instance = new M();
	//
	private Map<Class<?>, BaseManager> managers = new HashMap<Class<?>, BaseManager>();
	private boolean initialized;

	private M() {
	}

	public static M instance() {
		return instance;
	}

	public static <T> T get(Class<T> mc) {
		return (T) instance.managers.get(mc);
	}

	public synchronized void initialize() {
		if (initialized) {
			throw new RuntimeException("Invalid call !");
		}
		List<Class> list = Play.classloader.getAssignableClasses(BaseManager.class);
		for (Class c : list) {
			try {
				Logger.info("Creating manager: [%s]", c.getSimpleName());
				BaseManager m = (BaseManager) c.newInstance();
				managers.put(m.getClass(), m);
			} catch (Exception e) {
				Logger.error(e, "FATAL !!! Error on create !");
				System.exit(-1);
			}
		}
		List<BaseManager> mglist = new ArrayList<BaseManager>(managers.values());
		Collections.sort(mglist, new Comparator<BaseManager>() {
			@Override
			public int compare(BaseManager o1, BaseManager o2) {
				return o1.priority() - o2.priority();
			}
		});
		for (BaseManager mng : mglist) {
			try {
				Logger.info("Initializing manager: [%s]", mng.getClass().getSimpleName());
				mng.doInitialize();
			} catch (Exception e) {
				Logger.error(e, "FATAL !!! Error on initialize !");
				System.exit(-1);
			}
		}
	}

}
