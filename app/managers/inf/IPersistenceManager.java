package managers.inf;

import java.util.List;

import models.BaseModel;

public interface IPersistenceManager {

	<T extends BaseModel> T findById(Class<T> model, String id);

	<T extends BaseModel> List<T> find(Class<T> model, String query, Object... params);

	<T extends BaseModel> T findFirst(Class<T> model, String query, Object... params);

	<T extends BaseModel> T save(T model);

	<T extends BaseModel> void save(final T model, boolean async);

}
