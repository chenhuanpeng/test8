package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HealthyKnowledge {
    /**
     * 疾病编码
     * @mock 00023
     */
    private String hkCode;
    /**
     * 疾病名称
     * @mock 高血压
     */
    private String hkName;
    /**
     * 推荐内容
     * @mock 推荐内容
     */
    private String hkDesc;

	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;

	/**
	 * 健教知识属性内容
	 */
	private List<Attr> attrs;

	@Data
    public static class Attr{
		/**
		 * 标题
		 */
    	private String title;
		/**
		 * 内容
		 */
		private String content;
	}

	public HealthyKnowledge() {
	}

	public HealthyKnowledge(String hkCode, String hkName, String hkDesc) {
		this.hkCode = hkCode;
		this.hkName = hkName;
		this.hkDesc = hkDesc;
	}
}
