package com.testcode.webadmin.persistence.repository;

import com.testcode.webadmin.persistence.domain.App;
import com.testcode.webadmin.persistence.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by mykolaka.
 */
public interface AppRepository extends CrudRepository<App, Integer> {
	Set<App> getAllByUser(User user);
}
