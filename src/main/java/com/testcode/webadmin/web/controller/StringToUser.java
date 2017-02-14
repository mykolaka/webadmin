package com.testcode.webadmin.web.controller;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by mykolaka.
 */
@Component
public class StringToUser implements Converter<String, User> {

	@Autowired
	private UserService userService;

	@Override
	public User convert(String id) {
		if (id != null && !id.isEmpty()) {
			return userService.get(Integer.valueOf(id));
		}
		return null;
	}
}
