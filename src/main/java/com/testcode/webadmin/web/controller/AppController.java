package com.testcode.webadmin.web.controller;

import com.testcode.webadmin.persistence.domain.App;
import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.persistence.domain.UserRole;
import com.testcode.webadmin.service.AppService;
import com.testcode.webadmin.service.UserService;
import com.testcode.webadmin.web.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by mykolaka.
 */
@Controller
@SessionAttributes(types = App.class)
@RequestMapping("/app")
public class AppController {

	@Autowired
	private AppService appService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserHolder userHolder;

	@ModelAttribute("availableUsers")
	public List<User> getAvailableUsers() {
		return userService.getUsersByRole(UserRole.PUBLISHER);
	}

	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable("id") App app, Model model) {
		if (app == null) {
			return "redirect:/";
		}
		model.addAttribute("app", app);
		model.addAttribute("isEditAllowed", isEditAllowed(app));
		model.addAttribute("isPublisher", true);
		return "/app/view";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveApp(@ModelAttribute("app") @Valid App app, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "app/create";
		} else {
			appService.save(app);
		}
		return "redirect:/app/view/" + app.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") App app, Model model) {
		if (app == null) {
			return "redirect:/";
		}
		model.addAttribute("app", app);
		model.addAttribute("isPublisher", userHolder.getCurrentUser().getRole().equals(UserRole.PUBLISHER));
		return "/app/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		App application = appService.create();
		if (userHolder.getCurrentUser().getRole().equals(UserRole.PUBLISHER)) {
			//Publisher can create application only for current user (himself)
			application.setUser(userHolder.getCurrentUser());
			model.addAttribute("isPublisher", true);
		}
		model.addAttribute("app", application);
		return "/app/create";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("app") @Valid App app, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/app/edit";
		} else {
			appService.save(app);
			return "redirect:/app/view/" + app.getId();
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") App app, RedirectAttributes redirectAttributes) {
		if (app == null) {
			return "redirect:/";
		}
		try {
			appService.delete(app);
		} catch (DataIntegrityViolationException e) {
			return "redirect:/app/view/{id}";
		}
		return "redirect:/";
	}

	private boolean isEditAllowed(App application) {
		boolean result = true;
		//Publish can edit only his applications
		if (UserRole.PUBLISHER.equals(userHolder.getCurrentUser().getRole()) && !userHolder.getCurrentUser().getId()
				.equals(application.getUser().getId())) {
			result = false;
		}
		return result;
	}

}

