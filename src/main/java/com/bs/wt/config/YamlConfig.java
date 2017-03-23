package com.bs.wt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author bangsun
 *
 */
@ConfigurationProperties(prefix = "yml", locations = "classpath:wt.yml")
public class YamlConfig {
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
