package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]药品商品映射(WmkgDrugGoodsMapping)实体类
 *
 * @author linkeke
 * @since 2024-08-30 09:08:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugGoodsMapping implements Serializable {
	private static final long serialVersionUID = -39950978255778014L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 通用药品编码
	 */
	private String drugCode;
	/**
	 * 商品药品编码
	 */
	private String drugGoodsCode;


}

