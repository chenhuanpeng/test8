package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理检查指标(WmkgReasonExamItem)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonExamItem implements Serializable {
	private static final long serialVersionUID = -29828949498502574L;
	/**
	 * 检查编码
	 */
	private String examCode;
	/**
	 * 检查名称
	 */
	private String examName;
	/**
	 * 检查指标
	 */
	private String examItem;
	/**
	 * 检查指标单位
	 */
	private String unit;


}

