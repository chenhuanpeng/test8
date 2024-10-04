package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理检查(WmkgReasonExam)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonExam implements Serializable {
    private static final long serialVersionUID = -53531866119388705L;
    /**
     * 检查编码
     */
    private String examCode;
    /**
     * 检查名称
     */
    private String examName;


}

