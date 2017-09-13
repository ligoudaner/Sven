package com.ourslook.production.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 朋友圈点赞和评论
 * 
 * @author Sven
 * @email 1050676672@qq.com
 * @date 2017-09-05 17:29:33
 */
public class MomentsInteractionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private Long id;
	//朋友圈id
	private Long momentId;
	//类型:1：点赞、2：评论
	private Integer type;
	//
	private Long userId;
	//回复人id
	private Long replyToUserId;
	//回复内容，点赞时为空
	private String content;
	//
	private Integer status;
	//
	private Long createUser;
	//
	private Date createTime;

	/**
	 * 设置：主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：朋友圈id
	 */
	public void setMomentId(Long momentId) {
		this.momentId = momentId;
	}
	/**
	 * 获取：朋友圈id
	 */
	public Long getMomentId() {
		return momentId;
	}
	/**
	 * 设置：类型:1：点赞、2：评论
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型:1：点赞、2：评论
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：回复人id
	 */
	public void setReplyToUserId(Long replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	/**
	 * 获取：回复人id
	 */
	public Long getReplyToUserId() {
		return replyToUserId;
	}
	/**
	 * 设置：回复内容，点赞时为空
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：回复内容，点赞时为空
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：
	 */
	public Long getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
