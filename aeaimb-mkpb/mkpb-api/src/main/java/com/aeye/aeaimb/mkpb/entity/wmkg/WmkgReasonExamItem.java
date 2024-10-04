package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理检查指标(WmkgReasonExamItem)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonExamItem implements Serializable {
    private static final long serialVersionUID = -90282835173533590L;
    /**
     * 检查编码
     */
    private String examCode;
    /**
     * 检查名称
     */
    private String examName;
    /**
     * 检查指标
     */
    private String examItem;
    /**
     * 指标单位
     */
    private String unit;


}

