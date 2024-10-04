package com.aeye.aeaimb.mkpb.entity.wmkg;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理疾病业务分组(WmkgReasonCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCategory implements Serializable {
	private static final long serialVersionUID = -48621229343659519L;
	/**
	 * 业务分组类型
	 */
	private String factorCate;
	/**
	 * 业务分组名称
	 */
	private String factorName;
	/**
	 * 业务分值比例
	 */
	private String factorRatio;
	/**
	 * 业务分组排序
	 */
	private Integer factorSort;
	/**
	 * 推理ID
	 */
	private String reasonId;


}

