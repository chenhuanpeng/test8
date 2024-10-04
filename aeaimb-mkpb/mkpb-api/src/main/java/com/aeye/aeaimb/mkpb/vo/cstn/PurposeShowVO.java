package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 */
@Data
@Schema(description = "目标展示对象")
public class PurposeShowVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
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
	 * 主目标ID
	 */
	private String parentPurpose;


	/**
	 * 主目标名称
	 */
	private String parentPurposeName;

	/**
	 * Meta映射
	 */
	private String metaCode;

	/**
	 * 创建人
	 */
	private String createBy;

	/**
	 * 修改人
	 */
	private String updateBy;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;

	/**
	 * 删除标志 0 正常
	 */
	private String delFlag;

	//选择条件
	List<ConditionVO> selConditionList;

	//跳过条件
	List<ConditionVO> skipConditionList;


}
