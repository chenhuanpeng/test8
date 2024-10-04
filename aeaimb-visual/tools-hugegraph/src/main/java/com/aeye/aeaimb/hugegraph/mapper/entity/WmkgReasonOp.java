package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理手术(WmkgReasonOp)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonOp implements Serializable {
	private static final long serialVersionUID = -58736596453356703L;
	/**
	 * 手术编码
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String opCode;
	/**
	 * 手术名称
	 */
	private String opName;


}

