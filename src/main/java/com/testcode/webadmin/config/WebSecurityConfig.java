package com.testcode.webadmin.config;

import com.testcode.webadmin.persistence.domain.User;
import com.testcode.webadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

/**
 * Created by mykolaka.
 */
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "com.testcode.webadmin")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and()
				.logout().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inMemoryUserDetailsManager());
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		Properties users = new Properties();
		for (User user : userService.findAll()) {
			users.put(user.getUserName(), user.getPassword() + ",enabled," + user.getRole().toString());
		}
		return new InMemoryUserDetailsManager(users);
	}
}
