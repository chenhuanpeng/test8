package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]药品所属分类(WmkgDrugInCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugInCategory implements Serializable {
	private static final long serialVersionUID = 163992309297700003L;
	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
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

