package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 药品分类表(BaseDrugCategory)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugCategory implements Serializable {
	private static final long serialVersionUID = -51269997165316562L;
	/**
	 * 分类编码
	 */
	private String cateCode;
	/**
	 * 分类名称
	 */
	private String cateName;
	/**
	 * 上级分类
	 */
	private String parentCode;
	/**
	 * 排序
	 */
	private Integer cateSort;


}

