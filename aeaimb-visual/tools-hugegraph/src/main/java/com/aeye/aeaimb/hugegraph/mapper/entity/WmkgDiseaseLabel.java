package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 疾病标签表(WmkgDiseaseLabel)实体类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiseaseLabel implements Serializable {
	private static final long serialVersionUID = -56356229060289581L;
	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
	/**
	 * 疾病标签
	 */
	private String diseaseLabel;
	/**
	 * 排序
	 */
	private Integer diseaseSort;

	/**
	 * 疾病提示级别：1高、2中、3低
	 */
	private String diseaseLvl;
	/**
	 * 疾病标签简称
	 */
	private String diseaseLabelAs;


}

