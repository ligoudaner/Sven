package com.ourslook.production.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ourslook.production.entity.MomentsEntity;
import com.ourslook.production.service.MomentsService;
import com.ourslook.production.utils.PageUtils;
import com.ourslook.production.utils.Query;
import com.ourslook.production.utils.R;


/**
 * 朋友圈
 * 
 * @author Sven
 * @email 1050676672@qq.com
 * @date 2017-09-05 17:29:33
 */
@RestController
@RequestMapping("moments")
public class MomentsController {
	@Autowired
	private MomentsService momentsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("moments:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MomentsEntity> momentsList = momentsService.queryList(query);
		int total = momentsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(momentsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("moments:info")
	public R info(@PathVariable("id") Integer id){
		MomentsEntity moments = momentsService.queryObject(id);
		
		return R.ok().put("moments", moments);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("moments:save")
	public R save(@RequestBody MomentsEntity moments){
		momentsService.save(moments);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("moments:update")
	public R update(@RequestBody MomentsEntity moments){
		momentsService.update(moments);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("moments:delete")
	public R delete(@RequestBody Integer[] ids){
		momentsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
