package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class LabTestReport {
	/**
	 * 检查/检验/操作流水单号
	 */
	private String testOrderId;
	/**
	 * 检查/检验/操作申请时间 格式：yyyy-MM-dd HH:mm:ss
	 */
	private String testApplyTime;
	/**
	 * 检查/检验编码（检查字典ID）
	 * @mock t0000000000108
	 */
	private String testCode;
	/**
	 * 检查/检验名称
	 * @mock 血常规
	 */
	protected String testName;
	/**
	 * 类型 检验：1 ，检查：2, 操作：3
	 * @mock 1
	 */
	protected String testType;
	/**
	 * 注意事项
	 */
	protected String notes;
	/**
	 * 推荐归因
	 */
	protected List<LabTestReportItem> labTestReportItems;
	/**
	 * 检验样本
	 * @mock 血清
	 */
	protected String testSample;
	/**
	 * 检查部位
	 * @mock 胸部CT
	 */
	protected String testPosition;
	/**
	 * 备注
	 * @mock 没有备注
	 */
	protected String remark;
	/**
	 * 作废：0
	 * 正常：1（状态节点不传或者传1均认为是正常数据）
	 */
	protected Integer state;

	/**
	 * 数量
	 * @mock 1
	 */
	protected String number;

	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;
	/**
	 * 标记 his 已经填了
	 */
	protected boolean hisMark;

	/**
	 * 金标准标记（true/false）
	 * @Mock false
	 */
	private boolean goldFlag = false;

	//医保匹配标记
	private  Integer yibaoMatchFlag=0;

	public LabTestReport() {
	}
	public LabTestReport(String testCode, String testName, String testType){
		this.testCode = testCode;
		this.testName = testName;
		this.testType = testType;
	}
	public LabTestReport(String testCode, String testName, String testType, String testSample, String testPosition, String remark) {
		this.testCode = testCode;
		this.testName = testName;
		this.testType = testType;
		this.testSample = testSample;
		this.testPosition = testPosition;
		this.remark = remark;
	}
}
