package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理检验(WmkgReasonLabtest)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonLabtest implements Serializable {
	private static final long serialVersionUID = -12540064808856969L;
	/**
	 * 检验编码
	 */
	@TableId(type = com.baomidou.mybatisplus.annotation.IdType.ASSIGN_UUID)
	private String labtestCode;
	/**
	 * 检验名称
	 */
	private String labtestName;


}

