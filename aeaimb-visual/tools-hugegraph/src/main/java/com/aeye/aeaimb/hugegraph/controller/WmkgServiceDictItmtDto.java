package com.aeye.aeaimb.hugegraph.controller;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * [西医]服务项目字典(WmkgServiceDict)实体类
 *
 * @author linkeke
 * @since 2024-07-22 11:54:45
 */
@Data
public class WmkgServiceDictItmtDto implements Serializable {
    private static final long serialVersionUID = -46024739389461794L;
    /**
     * 指标编码
     */
	@ExcelProperty(value = "指标编码")
    private String itemCode;
    /**
     * 指标名称
     */
	@ExcelProperty(value = "指标名称")
    private String itemName;


}

