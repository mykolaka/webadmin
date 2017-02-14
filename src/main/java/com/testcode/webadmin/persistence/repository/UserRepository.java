package com.testcode.webadmin.persistence.repository;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.domain.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by mykolaka.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
	User getUserByUserName(String userName);

	List<User> getUserByRole(UserRole userRole);

}
