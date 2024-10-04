package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则条件症状要素(WmkgReasonCdnSympElmt)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnSympElmt implements Serializable {
    private static final long serialVersionUID = 262153431798064119L;
    /**
     * 条件ID
     */
    private String cdnId;
    /**
     * 症状编码
     */
    private String symptomCode;
    /**
     * 症状名称
     */
    private String symptomName;
    /**
     * 因子编码
     */
    private String factorType;
    /**
     * 因子名称
     */
    private String factorName;
    /**
     * 因子值
     */
    private String factorValue;
    /**
     * 条件排序
     */
    private Integer factorSort;


}

