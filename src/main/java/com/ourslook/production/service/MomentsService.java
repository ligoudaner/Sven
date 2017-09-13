package com.ourslook.production.service;

import com.ourslook.production.entity.MomentsEntity;

import java.util.List;
import java.util.Map;

/**
 * 朋友圈
 * 
 * @author Sven
 * @email 1050676672@qq.com
 * @date 2017-09-05 17:29:33
 */
public interface MomentsService {
	
	MomentsEntity queryObject(Integer id);
	
	List<MomentsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MomentsEntity moments);
	
	void update(MomentsEntity moments);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
