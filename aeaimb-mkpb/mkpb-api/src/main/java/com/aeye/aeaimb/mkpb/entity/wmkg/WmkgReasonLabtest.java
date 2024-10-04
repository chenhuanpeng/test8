package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理检验(WmkgReasonLabtest)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonLabtest implements Serializable {
    private static final long serialVersionUID = 559150082174839296L;
    /**
     * 检验编码
     */
    private String labtestCode;
    /**
     * 检验名称
     */
    private String labtestName;


}

