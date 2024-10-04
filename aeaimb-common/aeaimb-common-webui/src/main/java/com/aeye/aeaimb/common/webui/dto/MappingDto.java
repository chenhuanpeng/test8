package com.aeye.aeaimb.common.webui.dto;

import lombok.Data;

import java.util.List;

/**
 * 专门处理映射数据的实体
 */
@Data
public class MappingDto {
	/**
	 * 项目编码
	 */
	private String itemCode;
	/**
	 * 项目名称
	 */
	private String itemName;

	/**
	 * 映射的目标数据列表
	 */
	private List<MappingDto> mappings;

	public MappingDto() {
	}

	public MappingDto(String itemCode, String itemName) {
		this.itemCode = itemCode;
		this.itemName = itemName;
	}
}
