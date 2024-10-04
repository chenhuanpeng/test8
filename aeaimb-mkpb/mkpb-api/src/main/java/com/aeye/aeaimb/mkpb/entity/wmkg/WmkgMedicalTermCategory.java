package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]医疗术语章节分类(WmkgMedicalTermCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-24 10:55:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgMedicalTermCategory implements Serializable {
	private static final long serialVersionUID = -95574995135018609L;
	/**
	 * 类目编码
	 */
	private String cateCode;
	/**
	 * 类目名称
	 */
	private String cateName;
	/**
	 * 1:章 2:节 3:类目，4:亚目
	 */
	private String cateType;
	/**
	 * 类目场景：手术、疾病
	 */
	private String cateFor;
	/**
	 * 分类版本
	 */
	private String normType;
	/**
	 * 父类目
	 */
	private String cateParentCode;


}

