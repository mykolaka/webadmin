package com.testcode.webadmin.service.impl;

import com.testcode.webadmin.persistence.domain.App;
import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.repository.AppRepository;
import com.testcode.webadmin.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by mykolaka.
 */
@Service
public class AppServiceImpl extends CrudServiceImpl<App, Integer> implements AppService {

	@Autowired
	private AppRepository appRepository;

	@Override
	public App create() {
		return new App();
	}

	@Override
	public CrudRepository<App, Integer> getRepository() {
		return appRepository;
	}

	@Override
	public Set<App> getAllByUser(User user) {
		return appRepository.getAllByUser(user);
	}
}
