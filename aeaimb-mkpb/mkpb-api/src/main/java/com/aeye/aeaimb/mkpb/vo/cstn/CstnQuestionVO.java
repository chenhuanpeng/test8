package com.aeye.aeaimb.mkpb.vo.cstn;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@Schema(description = "问诊问题展示对象")
public class CstnQuestionVO implements Serializable {

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
	 * 问题标题
	 */
	private String qaTitle;

	/**
	 * 问题提示
	 */
	private String qaTip;

	/**
	 * 选项来源:算法、自定义
	 */
	private String qaFrom;

	/**
	 * 问题排序
	 */
	private Integer qaSort;

	/**
	 * 选值类型：单选、多选
	 */
	private String qaSelect;

	/**
	 * 选项类型：选择、时间、自定义
	 */
	private String qaDisplayType;

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

	/**
	 * 选择条件
	 */
	private String selCondition;

	/**
	 * 跳过条件
	 */
	private String skipCondition;


}
