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

package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.mapper.entity.ColumnEntity;
import com.aeye.aeaimb.hugegraph.mapper.entity.TableEntity;

import java.util.List;
import java.util.Map;

/**
 * 列属性
 *
 * @author cdssx code generator
 * @date 2023-02-06 20:34:55
 */
public interface GenTableService  {

	/**
	 * 获取默认配置信息
	 * @return 默认配置信息
	 */
	Map<String, Object> getGeneratorConfig();


	/**
	 * 查询指定数据源下的所有表格
	 * @param dsName 数据源名称
	 * @return 所有表格的列表
	 */
	List<Map<String, Object>> queryDsAllTable(String dsName);

	/**
	 * 查询指定数据源的表信息
	 * @param tableName 表格名
	 * @return 表信息
	 */
	Map<String, String> queryTable(String dsName, String tableName);

	/**
	 * 查询指定数据源的表信息
	 * @param dsName 数据源
	 * @param tableName 表格名
	 * @return 表信息
	 */
	TableEntity queryTableEntity(String dsName, String tableName);

	/**
	 * 查询指定数据源和表名的列信息
	 * @param dsName 数据源名称
	 * @param tableName 表名
	 * @return 列信息列表
	 */
	List<Map<String, String>> queryColumn(String dsName, String tableName);

	/**
	 *  查询指定数据源和表名的列信息
	 * @param dsName 数据源名称
	 * @param tableName 表名
	 * @return 列信息列表
	 */
	List<ColumnEntity> queryColumnEntity(String dsName, String tableName);

}
