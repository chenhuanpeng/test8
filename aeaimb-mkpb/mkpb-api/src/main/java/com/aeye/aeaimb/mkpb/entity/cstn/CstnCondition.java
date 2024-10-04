package com.aeye.aeaimb.mkpb.entity.cstn;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * [问诊]条件
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("cstn_condition")
public class CstnCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 外键ID
     */
    @TableField("ref_id")
    private String refId;

    /**
     * 外键类型：目标条件、问题条件、路径条件
     */
    @TableField("ref_type")
    private String refType;

    /**
     * 条件简称
     */
    @TableField("condition_name")
    private String conditionName;

    /**
     * 条件判定：选择条件、跳过条件
     */
    @TableField("condition_type")
    private String conditionType;

    /**
     * 条件排序
     */
    @TableField("condition_sort")
    private Integer conditionSort;

    /**
     * 运算符：等于、大于、小于、大于小于、小于等于、在范围、不在范围
     */
    @TableField("op")
    private String op;

    /**
     * 运算值
     */
    @TableField("op_val")
    private String opVal;

    /**
     * 运算单位
     */
    @TableField("op_unit")
    private String opUnit;


	/**
	 * 元数据ID
	 */
	@TableField("meta_id")
	private String metaId;


	/**
	 * 元数据编码
	 */
	@TableField("meta_code")
	private String metaCode;

}
