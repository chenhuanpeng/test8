package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理检验指标(WmkgReasonLabtestItem)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonLabtestItem implements Serializable {
	private static final long serialVersionUID = -73165476543023841L;
	/**
	 * 检验编码
	 */
	private String labtestCode;
	/**
	 * 检验名称
	 */
	private String labtestName;
	/**
	 * 指标名称
	 */
	private String labtestItem;
	private String unit;


}

