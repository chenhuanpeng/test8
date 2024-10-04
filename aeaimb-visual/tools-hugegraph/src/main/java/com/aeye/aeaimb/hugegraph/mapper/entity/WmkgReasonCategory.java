package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理疾病业务分组(WmkgReasonCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCategory implements Serializable {
	private static final long serialVersionUID = 476647127816113932L;
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
	private Object factorRatio;
	/**
	 * 业务分组排序
	 */
	private Integer factorSort;
	/**
	 * 推理ID
	 */
	private String reasonId;


}

