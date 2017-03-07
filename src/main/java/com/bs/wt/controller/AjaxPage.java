package com.bs.wt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.wt.bean.PushForm;
import com.bs.wt.service.PostService;

@Controller
@RequestMapping("/api")
public class AjaxPage {
	
	@Autowired
	private PostService postService;
	
	static final int PAGE = 10;
	
	@RequestMapping("ajax")
	public String ajax(Model model){
		model.addAttribute("list", list(0));
		int indexCount = postService.getIndexCount(null);
		model.addAttribute("counts",indexCount);
		model.addAttribute("current", 0);
		return "ajax/index";
	}
	
	@RequestMapping(value = "data",method = RequestMethod.POST)
	public String data(int page,Model model){
		model.addAttribute("list", list(page));
		model.addAttribute("current", page-1);
		return "ajax/data";
	}
	
	@RequestMapping("pagenation")
	public String pagenation(Model model){
		return "ajax/pagenation";
	}
	
	@ResponseBody
	@RequestMapping("page/nation")
	public Map<String,Object> page(){
		Map<String,Object> map = new HashMap<String,Object>();
		//map.put("list", list(0));
		map.put("counts", postService.getIndexCount(null));
		map.put("current", 0);
		return map;
	}
	
	private List<PushForm> list(int page){
		return postService.getIndexPostByPage(page, PAGE,null);
	}
	
}
