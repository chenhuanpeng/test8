package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理因子分类(WmkgReasonFactor)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonFactor implements Serializable {
    private static final long serialVersionUID = -39180847269080109L;
    /**
     * 因子分类编码
     */
    private String factorType;
    /**
     * 因子分类名称
     */
    private String factorName;
    /**
     * 因子分类排序
     */
    private Integer factorSort;
    /**
     * 因子父分类
     */
    private String parentFactor;
    /**
     * 因子层级
     */
    private Integer factorLvl;
    /**
     * 值性质:定性、定量
     */
    private String factorDefine;
    /**
     * 因子数据类型：多个值、单个值
     */
    private String factorDataType;
    /**
     * 因子数据单位
     */
    private String factorUnit;


}

