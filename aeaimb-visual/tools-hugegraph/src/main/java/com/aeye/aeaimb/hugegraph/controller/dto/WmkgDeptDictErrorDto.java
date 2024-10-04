package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 科室字典(BaseDeptDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 11:32:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDeptDictErrorDto implements Serializable {
	private static final long serialVersionUID = 215454561566358457L;
	/**
	 * 科室编码
	 */
	@ExcelProperty(value = "科室编码")
	private String deptCode;
	/**
	 * 科室名称
	 */
	@ExcelProperty(value = "科室名称")
	private String deptName;
	/**
	 * 父科室
	 */
	@ExcelProperty(value = "父级科室编码")
	private String parentCode;
	/**
	 * 排序
	 */
	@ExcelProperty(value = "序号")
	private Integer sort;

	/**
	 * 错误信息
	 */
	@ExcelProperty(value = "错误信息")
	private String errorMsg;

}

