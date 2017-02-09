package com.bs.wt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.wt.bean.PushForm;
public interface PostMapper {
	int save(PushForm post);
	List<PushForm> postList(int type);
	PushForm dayupById(int postId);
	int getIndexCount(@Param("searchType")Integer searchType);
	List<PushForm> getIndexPostByPage(@Param("page")int page,@Param("size")int size,@Param("searchType")Integer searchType);
	List<PushForm> getTopTenPost();
	void deletePost(int uuid);
}
