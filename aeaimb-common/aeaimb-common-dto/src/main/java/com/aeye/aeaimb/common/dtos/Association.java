package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class Association {
    /**
     * 关联症状编码
     * @mock t0009
     */
    private String code;
    /**
     * 关联症状名称
     * @mock 咳嗽
     */
    private String name;
	/**
	 * 归因
	 */
	private String desc;

	public Association() {
	}

	public Association(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
