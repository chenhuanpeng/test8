package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class TcmRecomInitItem extends CommMark {
	/**
	 * 分类标识
	 */
	private String categoryId;
	/**
	 * 知识库编码
	 */
	private String kgTypeCode;

	public TcmRecomInitItem(String categoryId) {
		this.categoryId = categoryId;
	}

	public TcmRecomInitItem(String code, String name, String categoryId) {
		super(code, name);
		this.categoryId = categoryId;
	}

	public TcmRecomInitItem(String code, String name, String desc, String categoryId) {
		super(code, name, desc);
		this.categoryId = categoryId;
	}
}
