package com.bs.wt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * ʹ�� @Configuration ���������ļ�
 * ��һ�ַ��� ��� JdbcConfig.java
 * @author bangsun
 *
 */
@Configuration
public class ProConfig {
	@Bean
	public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer() {
		ClassPathResource resource = new ClassPathResource("xiaomi.properties");
		PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(resource);
		return propertyPlaceholderConfigurer;
	}
}
