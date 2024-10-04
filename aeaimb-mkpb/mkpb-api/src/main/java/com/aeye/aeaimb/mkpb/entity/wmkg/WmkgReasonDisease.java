package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理疾病(WmkgReasonDisease)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonDisease implements Serializable {
    private static final long serialVersionUID = 816012451759403361L;
    /**
     * 疾病编码
     */
    private String diseaseCode;
    /**
     * 疾病名称
     */
    private String diseaseName;
    /**
     * 疾病排序
     */
    private Integer diseaseSort;


}

