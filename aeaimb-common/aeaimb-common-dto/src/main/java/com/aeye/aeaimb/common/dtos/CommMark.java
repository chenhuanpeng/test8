package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommMark implements Serializable {
	/**
     * 项目编码
	 * @mock 00000
	 */
	protected String code;
	/**
     * 项目名称
	 * @mock 不知道
	 */
	protected String name;
	/**
     * 项目描述
	 * @mock 推荐归因
	 */
	protected String desc;

	public CommMark() {
	}

	public CommMark(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public CommMark(String code, String name, String desc) {
		this.code = code;
		this.name = name;
		this.desc = desc;
	}
}
