/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the cdss4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.aeye.aeaimb.hugegraph.mapper.entity;

import lombok.Data;

/**
 * @author lengleng
 * @date 2018/07/29 列属性： https://blog.csdn.net/lkforce/article/details/79557482
 */
@Data
public class TableEntity {

	/**
	 * 数据库
	 */
	private String dbType;

	/**
	 * 表名称
	 */
	private String tableName;

	/**
	 * 数据库引擎
	 */
	private String engine;

	/**
	 * 表说明
	 */
	private String tableComment;
	/**
	 * 创建时间
	 */
	private String createTime;



}
