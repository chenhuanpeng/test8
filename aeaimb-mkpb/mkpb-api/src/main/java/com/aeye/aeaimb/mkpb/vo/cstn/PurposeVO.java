package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 */
@Data
@Schema(description = "问诊目标展示对象")
public class PurposeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 目标ID
	 */
	private String id;

	/**
	 * 目标名称
	 */
	private String purposeName;

	/**
	 * 目标类型:主目标、分支目标
	 */
	private String purposeType;

	/**
	 * 目标描述
	 */
	private String purposeDesc;

	/**
	 * 主目标
	 */
	private String parentPurpose;

	/**
	 * 选择条件
	 */
	private String selCondition;

	/**
	 * 跳过条件
	 */
	private String skipCondition;

	/**
	 * 是否循环
	 */
	private String isLoop;


}
