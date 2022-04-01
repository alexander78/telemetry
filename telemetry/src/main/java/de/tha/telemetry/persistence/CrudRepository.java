package de.tha.telemetry.persistence;

import java.util.List;

public interface CrudRepository<T> {

	List<T> readAll();
	
	T findById(int id);
	
	void update (T entity);
	
	T save(T entity);
}
