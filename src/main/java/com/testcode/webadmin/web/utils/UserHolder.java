package com.testcode.webadmin.web.utils;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by mykolaka.
 */
@Component
@Scope("singleton")
public class UserHolder {

	private UserService service;
	private User currentUser;

	private UserHolder(@Autowired UserService repository) {
		this.service = repository;
	}

	public User getCurrentUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = principal.toString();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		if (currentUser == null || !currentUser.getUserName().equals(username)) {
			currentUser = service.getUserByUserName(username);
		}
		return currentUser;

	}

}
