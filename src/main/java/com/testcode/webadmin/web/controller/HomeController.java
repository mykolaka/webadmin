package com.testcode.webadmin.web.controller;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.domain.UserRole;
import com.testcode.webadmin.service.AppService;
import com.testcode.webadmin.service.UserService;
import com.testcode.webadmin.web.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mykolaka.
 */
@Controller
public class HomeController {

	@Autowired
	private UserHolder userHolder;

	@Autowired
	private AppService appService;

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String getData(Model model) {
		User user = userHolder.getCurrentUser();
		model.addAttribute("currentUser", user);
		if (UserRole.PUBLISHER.equals(user.getRole())){
			model.addAttribute("apps", appService.getAllByUser(user));
		}
		if(UserRole.ADOPS.equals(user.getRole())) {
			model.addAttribute("apps", appService.findAll());
		}
		if (UserRole.ADMIN.equals(user.getRole()) || UserRole.ADOPS.equals(user.getRole())) {
			model.addAttribute("users", userService.findAll());
		}
		return "home";
	}
}
