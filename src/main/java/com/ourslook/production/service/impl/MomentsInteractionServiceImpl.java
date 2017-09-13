package com.ourslook.production.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.ourslook.production.dao.MomentsInteractionDao;
import com.ourslook.production.entity.MomentsInteractionEntity;
import com.ourslook.production.service.MomentsInteractionService;



@Service("momentsInteractionService")
public class MomentsInteractionServiceImpl implements MomentsInteractionService {
	@Autowired
	private MomentsInteractionDao momentsInteractionDao;
	
	@Override
	public MomentsInteractionEntity queryObject(Integer id){
		return momentsInteractionDao.queryObject(id);
	}
	
	@Override
	public List<MomentsInteractionEntity> queryList(Map<String, Object> map){
		return momentsInteractionDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return momentsInteractionDao.queryTotal(map);
	}
	
	@Override
	public void save(MomentsInteractionEntity momentsInteraction){
		momentsInteractionDao.save(momentsInteraction);
	}
	
	@Override
	public void update(MomentsInteractionEntity momentsInteraction){
		momentsInteractionDao.update(momentsInteraction);
	}
	
	@Override
	public void delete(Integer id){
		momentsInteractionDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		momentsInteractionDao.deleteBatch(ids);
	}
	
}
