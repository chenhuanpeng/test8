package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则组(WmkgReasonGroup)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonGroup implements Serializable {
	private static final long serialVersionUID = 519738948984968510L;
	/**
	 * 规则组ID
	 */
	private String groupId;
	/**
	 * 规则组名称
	 */
	private String groupName;
	/**
	 * 规则组关系:and/or
	 */
	private String groupCondition;
	/**
	 * 规则组排序
	 */
	private Integer groupSort;
	/**
	 * 推理ID
	 */
	private String reasonId;
	/**
	 * 业务分组类型
	 */
	private String factorCate;


}

