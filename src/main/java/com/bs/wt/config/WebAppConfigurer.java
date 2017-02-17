package com.bs.wt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bs.wt.csrf.CsrfIntercepter;
@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {
	
	@Autowired
	private CsrfIntercepter csrfIntercepter;

	@Override
	public void addInterceptors(InterceptorRegistry interceptorregistry) {
		// 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
		interceptorregistry.addInterceptor(csrfIntercepter).addPathPatterns("/**");
        super.addInterceptors(interceptorregistry);
	}

}
