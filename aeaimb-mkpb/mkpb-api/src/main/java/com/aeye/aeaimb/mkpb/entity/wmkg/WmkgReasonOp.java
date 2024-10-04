package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理手术(WmkgReasonOp)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:44:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonOp implements Serializable {
    private static final long serialVersionUID = -18729916790663922L;
    /**
     * 手术编码
     */
    private String opCode;
    /**
     * 手术名称
     */
    private String opName;


}

