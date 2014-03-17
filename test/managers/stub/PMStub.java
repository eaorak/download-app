package managers.stub;

import java.util.List;

import managers.inf.IPersistenceManager;
import models.BaseModel;

public class PMStub implements IPersistenceManager {

	private List<? extends BaseModel> models;

	public PMStub(List<? extends BaseModel> models) {
		this.models = models;
	}

	@Override
	public <T extends BaseModel> T findById(Class<T> model, String id) {
		return (T) models.get(0);
	}

	@Override
	public <T extends BaseModel> List<T> find(Class<T> model, String query, Object... params) {
		return (List<T>) models;
	}

	@Override
	public <T extends BaseModel> T findFirst(Class<T> model, String query, Object... params) {
		return (T) models.get(0);
	}

	@Override
	public <T extends BaseModel> T save(T model) {
		return (T) models.get(0);
	}

	@Override
	public <T extends BaseModel> void save(T model, boolean async) {
	}

}
