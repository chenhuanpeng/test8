package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 药品所属分类(BaseDrugInCategory)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugInCategory implements Serializable {
	private static final long serialVersionUID = 919414765120374580L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 药品编码
	 */
	private String drugCode;
	/**
	 * 分类编码
	 */
	private String cateCode;


}

