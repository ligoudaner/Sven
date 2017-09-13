package com.ourslook.production.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 朋友圈
 * 
 * @author Sven
 * @email 1050676672@qq.com
 * @date 2017-09-05 17:29:33
 */
public class MomentsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//用户Id
	private Long userId;
	//朋友圈内容
	private String content;
	//朋友圈图片，多张用逗号分割
	private String photos;
	//用户名:发朋友圈的人
	private String userName;
	//类型:1：点赞、2：评论
	private String type;
	//用户名,点赞或评论人名
	private String mUserName;
	//回复评论人
	private String replyToUserName;
	//回复内容，点赞时为空
	private String replyContent;
	//
	private Integer status;
	//
	private Long createUser;
	//
	private Date createTime;

	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：用户Id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户Id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：朋友圈内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：朋友圈内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：朋友圈图片，多张用逗号分割
	 */
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	/**
	 * 获取：朋友圈图片，多张用逗号分割
	 */
	public String getPhotos() {
		return photos;
	}
	/**
	 * 设置：用户名:发朋友圈的人
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名:发朋友圈的人
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：类型:1：点赞、2：评论
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型:1：点赞、2：评论
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：用户名,点赞或评论人名
	 */
	public void setMUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	/**
	 * 获取：用户名,点赞或评论人名
	 */
	public String getMUserName() {
		return mUserName;
	}
	/**
	 * 设置：回复评论人
	 */
	public void setReplyToUserName(String replyToUserName) {
		this.replyToUserName = replyToUserName;
	}
	/**
	 * 获取：回复评论人
	 */
	public String getReplyToUserName() {
		return replyToUserName;
	}
	/**
	 * 设置：回复内容，点赞时为空
	 */
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	/**
	 * 获取：回复内容，点赞时为空
	 */
	public String getReplyContent() {
		return replyContent;
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
