package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理症状(WmkgReasonSymptom)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:44:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonSymptom implements Serializable {
    private static final long serialVersionUID = 573188789119753104L;
    /**
     * 症状编码
     */
    private String symptomCode;
    /**
     * 症状名称
     */
    private String symptomName;


}

