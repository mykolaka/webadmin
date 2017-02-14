package com.testcode.webadmin.service;

import com.testcode.webadmin.persistence.domain.App;
import com.testcode.webadmin.persistence.domain.User;

import java.util.Set;

/**
 * Created by mykolaka.
 */
public interface AppService extends CrudService<App, Integer> {
	Set<App> getAllByUser(User user);
}
