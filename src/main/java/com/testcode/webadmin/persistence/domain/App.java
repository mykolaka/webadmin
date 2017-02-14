package com.testcode.webadmin.persistence.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mykolaka.
 */
@Entity
public class App {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Size(max = 255)
	@NotBlank
	private String name;
	@NotNull
	private AppType type;
	@NotEmpty
	@ElementCollection(targetClass = ContentType.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "AppToContentType", joinColumns = @JoinColumn(name = "id"))
	private Set<ContentType> contentTypes;
	@NotNull
	@ManyToOne
	private User user;

	public App() {
	}

	public App(String name, AppType type, Set<ContentType> contentTypes, User user) {
		this.name = name;
		this.type = type;
		this.contentTypes = contentTypes;
		this.user = user;
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

	public AppType getType() {
		return type;
	}

	public void setType(AppType type) {
		this.type = type;
	}

	public Set<ContentType> getContentTypes() {
		if (contentTypes == null) {
			contentTypes = new HashSet<>();
		}
		return contentTypes;
	}

	public void setContentTypes(Set<ContentType> contentTypes) {
		this.contentTypes = contentTypes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "App{" + "id=" + id + ", name='" + name + '\'' + ", type=" + type + ", contentTypes=" + Arrays
				.toString(getContentTypes().toArray()) + ", user=" + user.getName() + '}';
	}
}
