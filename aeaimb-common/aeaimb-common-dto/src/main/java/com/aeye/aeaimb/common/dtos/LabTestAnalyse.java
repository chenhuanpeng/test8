package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

@Data
public class LabTestAnalyse {
    /**
     * 检验项编码
     * @mock 00023
     */
    private String labTestItemCode;
    /**
     * 检验项名称
     * @mock 血常规5项
     */
    private String labTestItemName;
    /**
     * 检验结果英文名称
     * @mock HIC
     */
    private String labTestItemEnName;
    /**
     * 解读内容说明
     * @mock 解读内容说明
     */
    private String labTestDesc;

	/**
	 * 检查项处理意见
	 */
	private List<AdviceRule> labTestItemTexts;


}
