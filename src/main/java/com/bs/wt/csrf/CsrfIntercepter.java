package com.bs.wt.csrf;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CsrfIntercepter extends HandlerInterceptorAdapter {
	static final String CSRFNUMBER = "PPP007_";
	
	static String[] path = {"/cjl","/controllist"};

	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object obj, Exception exception) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("afterCompletion");
		super.afterCompletion(httpservletrequest, httpservletresponse, obj, exception);
	}

	@Override
	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("postHandle");
		super.postHandle(httpservletrequest, httpservletresponse, obj, modelandview);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		boolean result = true;
		String servletPath = request.getServletPath();
		for(String st : path){
			if(servletPath.equals(st)){
				Cookie[] cookies = request.getCookies();
				if (cookies != null && cookies.length > 0) {
					for (int i = 0; i < cookies.length; i++) {
						String name = cookies[i].getName();
						if (CSRFNUMBER.equals(name) && StringUtils.isNotEmpty(name)) {
							return result;
						}
					}
				}
				response.sendRedirect(request.getContextPath() + "/admin");
			}
		}
		return result;
	}
	
/*
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("a");
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("b");
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		String keyFromRequestParam = (String) request.getParameter(CSRFNUMBER);
		String keyFromCookies = "";
		boolean result = false;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String name = cookies[i].getName();
				if (CSRFNUMBER.equals(name)) {
					keyFromCookies = cookies[i].getValue();
				}
			}
		}

		if ((keyFromRequestParam != null && keyFromRequestParam.length() > 0
				&& keyFromRequestParam.equals(keyFromCookies)
				&& keyFromRequestParam.equals((String) request.getSession().getAttribute(CSRFNUMBER)))) {
			result = true;
		} else {
			request.getRequestDispatcher("/error/400").forward(request, response);
		}

		return result;
	}
*/
}
