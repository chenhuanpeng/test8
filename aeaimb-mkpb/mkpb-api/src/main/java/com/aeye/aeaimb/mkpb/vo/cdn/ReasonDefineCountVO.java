package com.aeye.aeaimb.mkpb.vo.cdn;

import lombok.Data;

import java.io.Serializable;

/**
 * 推理疾病定义(WmkgReasonDefine)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Data
public class ReasonDefineCountVO implements Serializable {
    private static final long serialVersionUID = 925491175499362698L;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
    /**
     * 疾病名称
     */
    private String diseaseName;

    /**
     * 方案数量
     */
    private Long count;

}

