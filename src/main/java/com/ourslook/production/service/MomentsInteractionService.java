package com.ourslook.production.service;

import com.ourslook.production.entity.MomentsInteractionEntity;

import java.util.List;
import java.util.Map;

/**
 * 朋友圈点赞和评论
 * 
 * @author Sven
 * @email 1050676672@qq.com
 * @date 2017-09-05 17:29:33
 */
public interface MomentsInteractionService {
	
	MomentsInteractionEntity queryObject(Integer id);
	
	List<MomentsInteractionEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MomentsInteractionEntity momentsInteraction);
	
	void update(MomentsInteractionEntity momentsInteraction);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
