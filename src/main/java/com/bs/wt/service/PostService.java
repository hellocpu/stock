package com.bs.wt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.wt.bean.PushForm;
import com.bs.wt.mapper.PostMapper;
import com.bs.wt.util.RandomDate;

@Service
public class PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	
	public int savePost(PushForm push){
		if(push.getType() == 1){
			// 明日必涨 ，时间随机
			push.setCreatetime(RandomDate.randomDate(1));
		}else if(push.getType() == 2){
			push.setCreatetime(RandomDate.randomDate(2));
		}else{
			push.setCreatetime(new Date());
		}
		postMapper.save(push);
		return 1;
	}
	
	public List<PushForm> postList(int type){
		return postMapper.postList(type);
	}
	
	public PushForm dayupById(int postId){
		return postMapper.dayupById(postId);
	}

	public int getIndexCount(Integer searchType) {
		return postMapper.getIndexCount(searchType);
	}
	
	public List<PushForm> getIndexPostByPage(int page,int size,Integer searchType){
		return postMapper.getIndexPostByPage(page * size, size,searchType);
	}
	
	public List<PushForm> getTopTenPost(){
		return postMapper.getTopTenPost();
	}

	public void deletePost(int uuid) {
		postMapper.deletePost(uuid);
	}
	
}
