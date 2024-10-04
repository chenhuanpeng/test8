package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则条件(WmkgReasonCdn)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdn implements Serializable {
    private static final long serialVersionUID = -95540346112797078L;
    /**
     * 条件ID
     */
    private String cdnId;
    /**
     * 因子类型
     */
    private String factorType;
    /**
     * 条件权重
     */
    private String factorWeight;
    /**
     * 条件排序
     */
    private Integer factorSort;
    /**
     * 否定条件:是/否
     */
    private String factorNeg;
    /**
     * 必选条件:是/否
     */
    private String factorMust;
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
    /**
     * 规则组ID
     */
    private String groupId;
    /**
     * 推理ID
     */
    private String reasonId;


}

