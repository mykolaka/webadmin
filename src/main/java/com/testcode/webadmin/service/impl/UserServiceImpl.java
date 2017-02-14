package com.testcode.webadmin.service.impl;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.domain.UserRole;
import com.testcode.webadmin.persistence.repository.UserRepository;
import com.testcode.webadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mykolaka.
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User, Integer> implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User create() {
		return new User();
	}

	@Override
	public CrudRepository<User, Integer> getRepository() {
		return userRepository;
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUserName(userName);
	}

	@Override
	public List<User> getUsersByRole(UserRole userRole) {
		return userRepository.getUserByRole(userRole);
	}
}
