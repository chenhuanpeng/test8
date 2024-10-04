package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]服务项目指标字典(WmkgServiceItemDict)实体类
 *
 * @author linkeke
 * @since 2024-09-06 11:09:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgServiceItemDict implements Serializable {
	private static final long serialVersionUID = 943272657301280554L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 指标代码
	 */
	private String itemCode;
	/**
	 * 指标名称
	 */
	private String itemName;
	/**
	 * 指标单位
	 */
	private String itemUnit;


}

