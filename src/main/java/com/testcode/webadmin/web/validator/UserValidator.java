package com.testcode.webadmin.web.validator;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by mykolaka.
 */
@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User target = (User) o;
		User existentUser = userService.getUserByUserName(target.getUserName());
		if (existentUser != null && !existentUser.getId().equals(target.getId())) {
			errors.rejectValue("userName", "user.duplicate", null, "User not unique");
		}
	}
}
