package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医疗术语章节分类(BaseMedicalTermCategory)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgMedicalTermCategory implements Serializable {
	private static final long serialVersionUID = -30478486906613402L;
	/**
	 * 类目编码
	 */
	@TableId(type = IdType.ASSIGN_UUID)
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
	 * 父类目
	 */
	private String cateParentCode;


}

