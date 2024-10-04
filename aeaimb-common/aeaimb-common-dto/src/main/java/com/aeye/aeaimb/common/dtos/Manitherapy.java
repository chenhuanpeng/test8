package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 操作治疗信息
 */
@Data
public class Manitherapy {
	/**
	 * 检查/检验/操作流水单号
	 */
	private String testOrderId;
	/**
	 * 检查/检验/操作申请时间 格式：yyyy-MM-dd HH:mm:ss
	 */
	private String testApplyTime;
    /**
     * 操作治疗编码
     * @mock xxxx
     */
    private String osCode;
    /**
     * 操作治疗名称
     * @mock 雾化吸入治疗
     */
    private String osName;
    /**
     * 操作治疗描述
     * @mock 吸入水汽
     */
    private String osDesc;
	/**
	 * 注意事项
	 */
	private String notes;
	/**
	 * 数量或次数
	 * @mock 1
	 */
	private Integer osNum;

	/**
	 * 数量单位
	 * @mock 009
	 */
	private String osNumUnit;

	/**
	 * 数量单位名称
	 * @mock 次
	 */
	private String osNumName;

	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;
	/**
	 * 标记 his 已经填了
	 */
	protected boolean hisMark;
	//医保匹配标记
	private Integer yibaoMatchFlag=0;

	public Manitherapy() {
	}

	public Manitherapy(String osCode, String osName) {
		this.osCode = osCode;
		this.osName = osName;
	}
}
