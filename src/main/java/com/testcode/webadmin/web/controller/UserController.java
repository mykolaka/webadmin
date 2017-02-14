package com.testcode.webadmin.web.controller;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.domain.UserRole;
import com.testcode.webadmin.service.UserService;
import com.testcode.webadmin.web.utils.UserHolder;
import com.testcode.webadmin.web.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.testcode.webadmin.persistence.domain.UserRole.ADOPS;
import static com.testcode.webadmin.persistence.domain.UserRole.PUBLISHER;

/**
 * Created by mykolaka.
 */
@Controller
@SessionAttributes(types = User.class)
@RequestMapping("/user")
public class UserController {

	private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator validator;

	@Autowired
	private UserHolder userHolder;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@ModelAttribute("availableRoles")
	public List<UserRole> getAvailableRoles() {
		switch (userHolder.getCurrentUser().getRole()) {
		case ADMIN:
			return Arrays.asList(ADOPS, PUBLISHER);
		case ADOPS:
			return Arrays.asList(PUBLISHER);
		}
		return Arrays.asList();
	}

	@Autowired
	public UserController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
		this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") User user, Model model) {
		if (user == null) {
			return "redirect:/";
		}
		model.addAttribute("user", user);
		model.addAttribute("canBeEdited", isEditEntityAllowed(user));
		return "/user/view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user/create";
		} else {
			userService.save(user);
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
			inMemoryUserDetailsManager.createUser(
					new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
							Arrays.asList(authority)));
		}
		return "redirect:/user/view/" + user.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") User user, Model model) {
		if (user == null) {
			return "redirect:/";
		}
		model.addAttribute("user", user);
		return "/user/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("user", userService.create());
		return "/user/create";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/user/edit";
		} else {
			userService.save(user);
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
			inMemoryUserDetailsManager.updateUser(
					new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
							Arrays.asList(authority)));
			return "redirect:/user/view/" + user.getId();
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") User user, RedirectAttributes redirectAttributes) {
		if (user == null) {
			return "redirect:/";
		}
		try {
			userService.delete(user);
			inMemoryUserDetailsManager.deleteUser(user.getUserName());
		} catch (DataIntegrityViolationException e) {
			return "redirect:/user/view/{id}";
		}
		return "redirect:/";
	}

	private boolean isEditEntityAllowed(User entity) {
		User currentUser = userHolder.getCurrentUser();
		return (UserRole.ADMIN == currentUser.getRole() && UserRole.ADMIN != entity.getRole()) || (
				UserRole.ADOPS == currentUser.getRole() && UserRole.PUBLISHER == entity.getRole());
	}
}

