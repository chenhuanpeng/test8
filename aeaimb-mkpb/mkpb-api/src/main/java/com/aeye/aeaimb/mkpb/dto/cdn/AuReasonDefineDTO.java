package com.aeye.aeaimb.mkpb.dto.cdn;

import lombok.Data;

import java.io.Serializable;

/**
 * 推理疾病定义(WmkgReasonDefine)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Data
public class AuReasonDefineDTO<T> implements Serializable {

	/**
	 * 推理疾病方案
	 */
	private AddReasonDefineDTO reasonDefine;

	/**
	 * 推理疾病业务分组
	 */
	private AddReasonCategory<T> reasonCategory;

}

