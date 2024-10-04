package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 疾病所属分类(BaseDiseaseInCategory)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiseaseInCategory implements Serializable {
	private static final long serialVersionUID = -73195638848629888L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 分类编码
	 */
	private String cateCode;


}

