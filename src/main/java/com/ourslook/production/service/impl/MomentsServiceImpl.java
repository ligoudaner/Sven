package com.ourslook.production.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ourslook.production.dao.MomentsDao;
import com.ourslook.production.entity.MomentsEntity;
import com.ourslook.production.service.MomentsService;



@Service("momentsService")
public class MomentsServiceImpl implements MomentsService {
	@Autowired
	private MomentsDao momentsDao;
	
	@Override
	public MomentsEntity queryObject(Integer id){
		return momentsDao.queryObject(id);
	}
	
	@Override
	public List<MomentsEntity> queryList(Map<String, Object> map){
		return momentsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return momentsDao.queryTotal(map);
	}
	
	@Override
	public void save(MomentsEntity moments){
		momentsDao.save(moments);
	}
	
	@Override
	public void update(MomentsEntity moments){
		momentsDao.update(moments);
	}
	
	@Override
	public void delete(Integer id){
		momentsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		momentsDao.deleteBatch(ids);
	}
	
}
