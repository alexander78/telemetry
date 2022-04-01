package de.tha.telemetry.persistence;

import java.util.ArrayList;
import java.util.List;

import de.tha.telemetry.model.Entity;

public class DefaultCrudRepository<T extends Entity> implements CrudRepository<T> {

	private List<T> master = new ArrayList<>();

	public DefaultCrudRepository() {
		super();
	}

	@Override
	public List<T> readAll() {
		return new ArrayList<>(master);
	}

	@Override
	public T findById(int id) {
		return master.stream().filter(entry -> entry.getId() == id).findFirst().orElse(null);
	}

	@Override
	public void update(T entity) {
		T findById = findById(entity.getId());
		int indexOf = master.indexOf(findById);
		master.set(indexOf, entity);
	}

	@Override
	public T save(T entity) {
		entity.setId((int) (Math.random() * Integer.MAX_VALUE));
		master.add(entity);
		return entity;
	}

}