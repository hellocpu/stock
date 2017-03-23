package com.bs.wt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bangsun
 *
 */
@ConfigurationProperties(prefix="pro",locations="classpath:wt.properties")
public class PropertiesConfig {
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
