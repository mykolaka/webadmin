package com.testcode.webadmin.service.impl;

import com.testcode.webadmin.service.CrudService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mykolaka.
 */
@Service
public abstract class CrudServiceImpl<T, ID extends Serializable> implements CrudService<T, ID> {

	@Transactional(readOnly = true)
	@Override
	public T get(ID id) {
		return getRepository().findOne(id);
	}

	@Transactional
	@Override
	public T save(T t) {
		return getRepository().save(t);
	}

	@Transactional
	@Override
	public void delete(ID id) {
		getRepository().delete(id);
	}

	@Transactional
	@Override
	public void delete(T t) {
		getRepository().delete(t);
	}

	@Transactional(readOnly = true)
	@Override
	public List<T> findAll() {
		List<T> result = new ArrayList<>();
		getRepository().findAll().forEach(result::add);
		return result;
	}

	@Transactional(readOnly = true)
	@Override
	public Long count() {
		return getRepository().count();
	}
}