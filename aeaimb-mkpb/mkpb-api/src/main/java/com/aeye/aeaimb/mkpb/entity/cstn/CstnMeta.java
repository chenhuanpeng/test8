package com.aeye.aeaimb.mkpb.entity.cstn;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * [问诊]问诊条件元数据
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("cstn_meta")
public class CstnMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 元数据名称
     */
    @TableField("meta_name")
    private String metaName;

    /**
     * 元数据编码
     */
    @TableField("meta_code")
    private String metaCode;

    /**
     * 元数据层级：1级，2级，3级
     */
    @TableField("meta_lvl")
    private String metaLvl;

    /**
     * 元数据父级
     */
    @TableField("parent_meta")
    private String parentMeta;

    /**
     * 元数据类型：下拉、文本、数字、区间
     */
    @TableField("meta_data_type")
    private String metaDataType;

    /**
     * 元数据选项：单选、多选
     */
    @TableField("meta_data_select")
    private String metaDataSelect;

    /**
     * 元数据来源：字典、数据表
     */
    @TableField("meta_source")
    private String metaSource;

    /**
     * 元数据SQL
     */
    @TableField("meta_sql")
    private String metaSql;

	/**
	 * 字典
	 */
	@TableField("meta_dict")
	private String metaDict;

	/**
	 * 单位
	 */
	@TableField("meta_unit")
	private String metaUnit;

	/**
	 * 场景
	 */
	@TableField("meta_sence")
	private String metaSence;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private LocalDateTime createTime;
}
