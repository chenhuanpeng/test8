package com.aeye.aeaimb.hugegraph.controller.dto;

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
public class WmkgServiceDictErrorDto implements Serializable {
    private static final long serialVersionUID = -46024739389461794L;
    /**
     * 服务项目编码
     */
	@ExcelProperty(value = "项目编码")
    private String serviceCode;
    /**
     * 服务项目名称
     */
	@ExcelProperty(value = "项目名称")
    private String serviceName;
    /**
     * 项目类别
     */
	@ExcelProperty(value = "项目类型")
    private String serviceType;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

