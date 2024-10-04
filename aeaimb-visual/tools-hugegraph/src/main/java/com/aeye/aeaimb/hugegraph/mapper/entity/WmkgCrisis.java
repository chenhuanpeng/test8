package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]危机值(WmkgCrisis)实体类
 *
 * @author linkeke
 * @since 2024-07-19 17:21:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgCrisis implements Serializable {
    private static final long serialVersionUID = 223051599642594476L;
    /**
     * id
     */
	@TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目类型:检验、检查
     */
    private String projectType;
    /**
     * 指标
     */
    private String indicatorName;
    /**
     * 指标值
     */
    private String indicatorValue;
    /**
     * 指标单位
     */
    private String indicatorUnit;
    /**
     * 适用年龄（天）
     */
    private String suitAge;
    /**
     * 适用科室
     */
    private String suitDept;
    /**
     * 危急描述
     */
    private String crisisDesc;


}

