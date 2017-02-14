package com.testcode.webadmin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

/**
 * Created by mykolaka.
 */
@SpringBootApplication
@EnableSpringConfigured
public class EntryPoint extends SpringBootServletInitializer {
	private static final Logger log = LoggerFactory.getLogger(EntryPoint.class);

	public static void main(String args[]) {
		SpringApplication.run(EntryPoint.class, args);
	}

	//For running on not embeded tomcat
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<EntryPoint> applicationClass = EntryPoint.class;
}

