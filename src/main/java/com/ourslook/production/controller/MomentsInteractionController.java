package com.ourslook.production.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ourslook.production.entity.MomentsInteractionEntity;
import com.ourslook.production.service.MomentsInteractionService;
import com.ourslook.production.utils.PageUtils;
import com.ourslook.production.utils.Query;
import com.ourslook.production.utils.R;


/**
 * 朋友圈点赞和评论
 * 
 * @author Sven
 * @email 1050676672@qq.com
 * @date 2017-09-05 17:29:33
 */
@RestController
@RequestMapping("/momentsinteraction")
public class MomentsInteractionController extends AbstractController{
	@Autowired
	private MomentsInteractionService momentsInteractionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("momentsinteraction:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MomentsInteractionEntity> momentsInteractionList = momentsInteractionService.queryList(query);
		int total = momentsInteractionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(momentsInteractionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("momentsinteraction:info")
	public R info(@PathVariable("id") Integer id){
		MomentsInteractionEntity momentsInteraction = momentsInteractionService.queryObject(id);
		
		return R.ok().put("momentsInteraction", momentsInteraction);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("momentsinteraction:save")
	public R save(@RequestBody MomentsInteractionEntity momentsInteraction){
		momentsInteraction.setCreateTime(new Date());
		momentsInteraction.setCreateUser(getUserId());
		momentsInteractionService.save(momentsInteraction);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("momentsinteraction:update")
	public R update(@RequestBody MomentsInteractionEntity momentsInteraction){
		momentsInteractionService.update(momentsInteraction);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("momentsinteraction:delete")
	public R delete(@RequestBody Integer[] ids){
		momentsInteractionService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
