package com.bs.wt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加载非application.yml的yml文件
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
