package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]药品商品字典(WmkgDrugGoodsDict)实体类
 *
 * @author linkeke
 * @since 2024-08-30 09:08:04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugGoodsDict implements Serializable {
	private static final long serialVersionUID = 165836707001745030L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 药品编码
	 */
	private String drugCode;
	/**
	 * 药品名称
	 */
	private String drugName;
	/**
	 * 通用名称
	 */
	private String drugNameGe;
	/**
	 * 药品本位码
	 */
	private String regCode;
	/**
	 * 批准文号
	 */
	private String approvalNumber;
	/**
	 * 剂型
	 */
	private String dosageForm;
	/**
	 * 规格
	 */
	private String specifications;
	/**
	 * 包装材质
	 */
	private String packagingMaterial;
	/**
	 * 最小包装数量
	 */
	private Integer packagingQuantity;
	/**
	 * 最小制剂单位
	 */
	private String formulationUnit;
	/**
	 * 最小包装单位
	 */
	private String packagingUnit;
	/**
	 * 药品企业
	 */
	private String manufactor;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 删除标志 0 正常
	 */
	private String delFlag;

}

