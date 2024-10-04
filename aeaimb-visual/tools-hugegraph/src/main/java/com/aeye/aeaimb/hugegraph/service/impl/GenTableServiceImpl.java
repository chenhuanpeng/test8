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
package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.GeneratorMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.ColumnEntity;
import com.aeye.aeaimb.hugegraph.mapper.entity.TableEntity;
import com.aeye.aeaimb.hugegraph.service.GenTableService;
import com.aeye.aeaimb.hugegraph.util.GenKit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 列属性
 *
 * @author cdssx code generator
 * @date 2023-02-06 20:34:55
 */
@Service
@RequiredArgsConstructor
public class GenTableServiceImpl implements GenTableService {

	@Override
	public Map<String, Object> getGeneratorConfig() {
		return null;
	}

	@Override
	public List<Map<String, Object>> queryDsAllTable(String dsName) {
		GeneratorMapper mapper = GenKit.getMapper(dsName);
		return mapper.queryTable();
	}

	@Override
	public Map<String, String> queryTable(String dsName,String tableName) {
		GeneratorMapper mapper = GenKit.getMapper(dsName);
		return mapper.queryTable(tableName, dsName);
	}

	@Override
	public TableEntity queryTableEntity(String dsName, String tableName) {
		GeneratorMapper mapper = GenKit.getMapper(dsName);
		return mapper.selectTableEntity(tableName, dsName);
	}

	@Override
	public List<Map<String, String>> queryColumn(String dsName, String tableName) {
		GeneratorMapper mapper = GenKit.getMapper(dsName);
		return mapper.selectMapTableColumn(tableName, dsName);
	}

	@Override
	public List<ColumnEntity> queryColumnEntity(String dsName, String tableName) {
		GeneratorMapper mapper = GenKit.getMapper(dsName);
		return mapper.selectTableColumn(tableName, dsName);
	}
}
