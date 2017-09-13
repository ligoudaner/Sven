package com.ourslook.production.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ourslook.production.entity.SysUserEntity;
import com.ourslook.production.utils.ShiroUtils;

/**
 * Controller公共组件
 * 
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
}
