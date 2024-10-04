package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class AgoraTokenParameter {

	/**
	 *	APPID 声网应用ID
	 */
	private String appid;

	/**
	 *	 声网添加RTC token时候可以选择uid或者account  ,
	 */
	private Integer uid;

	/**
	 * 频道名称
	 */
	private String channelName;

	/**
	 * 会话凭证
	 */
	private String token;

	/**
	 *	 声网添加RTC token时候可以选择uid或者account  ,
	 */
	private String account;

	/**
	 * 到期时间
	 */
	private Long expirationTime;

	/**
	 * 声网添加RTM token时候，会生成userid
	 */
	String userid;

	/**
	 * 凭证类型
	 */
	private String type;
}
