package com.testcode.webadmin.service;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.domain.UserRole;

import java.util.List;

/**
 * Created by mykolaka.
 */
public interface UserService extends CrudService<User, Integer> {
	User getUserByUserName(String userName);

	List<User> getUsersByRole(UserRole userRole);
}
