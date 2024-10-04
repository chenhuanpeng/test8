package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理表达式(WmkgReasonCdnExp)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnExp implements Serializable {
	private static final long serialVersionUID = 588630925084549760L;
	/**
	 * 条件ID
	 */
	private String cdnId;
	/**
	 * 配置值
	 */
	private String configValue;
	/**
	 * 运算符号：=（等于）、!=（不等于）、in ["","",""] （等于任意一个）、>（大于）、<（小于）、>=（大于等于）、<=（小于等于）、between [10,40]（介于范围）
	 */
	private String op;
	/**
	 * 目标值
	 */
	private String targetValue;
	/**
	 * 单位
	 */
	private String unit;


}

