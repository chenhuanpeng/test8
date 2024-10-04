package com.aeye.aeaimb.mkpb.dto.cdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 推理疾病业务分组(WmkgReasonCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Data
public class AddReasonCategory<T> implements Serializable {
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
	/**
	 * 推理规则组
	 */
	private List<AddReasonGroup<T>> reasonGroups;

}

