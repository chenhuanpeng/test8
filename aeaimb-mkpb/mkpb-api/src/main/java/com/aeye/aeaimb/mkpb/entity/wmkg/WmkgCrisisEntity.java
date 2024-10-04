package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * [西医]危机值(WmkgCrisis)实体类
 *
 * @author makejava
 * @since 2024-07-22 16:04:34
 */
@Data
@TableName("wmkg_crisis")
public class WmkgCrisisEntity implements Serializable {
    private static final long serialVersionUID = -24962639802206353L;
/**
     * id
     */
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

