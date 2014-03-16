package managers;

import java.util.List;

import models.BaseModel;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPAPlugin;
import play.db.jpa.JPQL;

/**
 * @author ender
 */
public class PersistenceManager extends BaseManager {

	private JPQL jpa;
	private ThreadManager trm;

	public PersistenceManager() {
	}

	@Override
	public void initialize() {
		jpa = play.db.jpa.JPQL.instance;
		trm = M.get(ThreadManager.class);
	}

	public <T extends BaseModel> T findById(Class<T> model, String id) {
		try {
			return (T) jpa.findById(model.getName(), id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T extends BaseModel> List<T> find(Class<T> model, String query, Object... params) {
		JPAQuery q = jpa.find(model.getName(), query, params);
		List<T> list = q.fetch();
		return list;
	}

	public <T extends BaseModel> T findFirst(Class<T> model, String query, Object... params) {
		List<T> list = find(model, query, params);
		return list.size() == 0 ? null : list.get(0);
	}

	public <T extends BaseModel> T save(T model) {
		return model.save();
	}

	public <T extends BaseModel> void save(final T model, boolean async) {
		if (!async) {
			save(model);
			return;
		}
		Runnable r = new Runnable() {
			@Override
			public void run() {
				play.Play.plugin(JPAPlugin.class).startTx(true);
				model.save();
				play.Play.plugin(JPAPlugin.class).closeTx(false);
			}
		};
		trm.execute(r);
	}

	@Override
	public int priority() {
		return 1;
	}

}
