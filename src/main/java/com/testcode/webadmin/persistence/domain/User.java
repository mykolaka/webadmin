package com.testcode.webadmin.persistence.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by mykolaka.
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Size(max = 255)
	@NotBlank
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotNull
	private UserRole role;
	@NotBlank
	private String userName;
	private String password;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private Set<App> apps;

	public User() {
	}

	public User(String name, String email, String userName, String password, UserRole role) {
		this.name = name;
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", role=" + role.name()
				+ ", userName='" + userName + '\'' + ", password='" + password + '\'' + '}';
	}
}
