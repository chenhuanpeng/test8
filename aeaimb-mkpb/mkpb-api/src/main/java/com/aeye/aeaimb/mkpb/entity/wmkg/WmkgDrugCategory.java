package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]药品分类表(WmkgDrugCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugCategory implements Serializable {
	private static final long serialVersionUID = -98450799468160886L;
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

