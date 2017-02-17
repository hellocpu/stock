package com.bs.wt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bs.wt.csrf.CsrfIntercepter;

@Configuration
public class InterceptConfig {
	@Bean
	public CsrfIntercepter csrfIntercepter(){
		return new CsrfIntercepter();
	}
}
