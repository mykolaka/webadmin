package com.testcode.webadmin.service;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mykolaka.
 */
public interface CrudService<T, ID extends Serializable> {
	T get(ID id);

	T save(T t);

	T create();

	void delete(ID id);

	void delete(T t);

	List<T> findAll();

	Long count();

	CrudRepository<T, ID> getRepository();

}
