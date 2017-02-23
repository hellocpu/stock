package com.bs.wt.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.wt.bean.Login;
import com.bs.wt.mongo.DfpMongo;


@Controller
public class AdminApi {
	
	@Value("${login.userName}")
	private String userName;
	@Value("${login.passWord}")
	private String passWord;
	
	private final static String LOGIN = "admin/admin";
	
	@Autowired
	private DfpMongo dfpMongo;
	
	@RequestMapping(value = "/admin",method=RequestMethod.GET)
	public String admin(Login login){
		return LOGIN;
	}
	
	@RequestMapping("/login_")
	public String login(@Valid Login login,BindingResult bindingResult,HttpServletResponse response,Model model){
		if(bindingResult.hasErrors()){
			return LOGIN;
        }
		if(!isLogin(login)){
			bindingResult.rejectValue("userName", "","用户名、密码错误.");
			return LOGIN;
		}
		
		// 写cookie
		writeCookie(response);
		return "redirect:/cjl";
	}
	
	private boolean isLogin(Login login){
		if(login.getUserName().equals(userName) && login.getPassWord().equals(passWord)){
			return true;
		}
		return false;
	}
	
	private void writeCookie(HttpServletResponse response){
		Cookie cookie = new Cookie("PPP007_",UUID.randomUUID().toString());
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}
	
	@ResponseBody
	@RequestMapping("mongo")
	public String testMongo(){
		return dfpMongo.findAll().size() +"";
		
	}
	
}
