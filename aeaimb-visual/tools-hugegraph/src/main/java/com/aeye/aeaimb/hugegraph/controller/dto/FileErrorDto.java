package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 临床指南表(TcmsClinicalGuide)实体类
 *
 * @author linkeke
 * @since 2024-06-11 16:58:16
 */
@Data
public class FileErrorDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;

    @ExcelProperty(value = "文件名")
    private String fileName;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;
}

