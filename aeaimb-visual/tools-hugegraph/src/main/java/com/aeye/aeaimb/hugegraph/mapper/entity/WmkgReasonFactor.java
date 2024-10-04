package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理因子分类(WmkgReasonFactor)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:35:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonFactor implements Serializable {
	private static final long serialVersionUID = -48350464779957246L;
	/**
	 * 因子分类编码
	 */
	@TableId
	private String factorType;
	/**
	 * 因子分类名称
	 */
	private String factorName;
	/**
	 * 因子分类排序
	 */
	private Integer factorSort;
	/**
	 * 因子父分类
	 */
	private String parentFactor;
	/**
	 * 因子层级
	 */
	private Integer factorLvl;
	/**
	 * 值性质:定性、定量
	 */
	private String factorDefine;
	/**
	 * 因子数据类型：多个值、单个值
	 */
	private String factorDataType;
	/**
	 * 因子数据单位
	 */
	private String factorUnit;


}

