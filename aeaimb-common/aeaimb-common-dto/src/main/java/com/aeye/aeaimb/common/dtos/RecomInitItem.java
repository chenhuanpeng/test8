package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class RecomInitItem extends CommMark{
	private String sort;
	/**
	 * 1: 症状, 2: 诊断
	 */
	private String type;
	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;

	public RecomInitItem() {
	}

	public RecomInitItem(String code, String name, String type) {
		super(code, name);
		this.type = type;
	}
}
