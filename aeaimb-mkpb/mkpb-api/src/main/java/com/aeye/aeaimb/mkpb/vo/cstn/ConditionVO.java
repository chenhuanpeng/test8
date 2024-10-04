package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenhuanpeng
 * @date 2024/09/05
 */
@Data
@Schema(description = "条件展示对象")
public class ConditionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 元数据ID
	 */
	private String conditionType;

	/**
	 * 条件简称
	 */
	private String conditionTypeName;

	/**
	 *  运算符
	 */
	private String op;

	/**
	 * 运算值
	 */
	private String targetValue;


	/**
	 * 单位
	 */
	private String unit;

}
