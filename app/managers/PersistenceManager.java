package managers;

import java.util.List;

import managers.inf.IPersistenceManager;
import models.BaseModel;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPQL;
import play.jobs.Job;

/**
 * @author ender
 */
public class PersistenceManager extends BaseManager implements IPersistenceManager {

	private JPQL jpa;
	private ThreadManager trm;

	public PersistenceManager() {
	}

	@Override
	public void initialize() {
		jpa = play.db.jpa.JPQL.instance;
		trm = M.get(ThreadManager.class);
	}

	@Override
	public <T extends BaseModel> T findById(Class<T> model, String id) {
		try {
			return (T) jpa.findById(model.getName(), id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends BaseModel> List<T> find(Class<T> model, String query, Object... params) {
		JPAQuery q = jpa.find(model.getName(), query, params);
		List<T> list = q.fetch();
		return list;
	}

	@Override
	public <T extends BaseModel> T findFirst(Class<T> model, String query, Object... params) {
		List<T> list = find(model, query, params);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public <T extends BaseModel> T save(T model) {
		return model.save();
	}

	@Override
	public <T extends BaseModel> void save(final T model, boolean async) {
		if (!async) {
			save(model);
			return;
		}
		new Job() {
			@Override
			public void doJob() {
				model.save();
			};
		}.now();
	}

	@Override
	public int priority() {
		return 1;
	}

}
