package com.testcode.webadmin.persistence.domain;

/**
 * Created by mykolaka.
 */
public enum UserRole {
	ADMIN, ADOPS, PUBLISHER;

	@Override
	public String toString() {
		return "ROLE_"+name();
	}
}
