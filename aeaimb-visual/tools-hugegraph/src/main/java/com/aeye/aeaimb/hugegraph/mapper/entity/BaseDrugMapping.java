package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医院药品映射(BaseDrugMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-22 18:07:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseDrugMapping implements Serializable {
	private static final long serialVersionUID = -19318004902839251L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 机构ID
	 */
	private String orgId;
	/**
	 * 药品编码
	 */
	private String drugCode;
	/**
	 * 药品名称
	 */
	private String drugName;
	/**
	 * 院方药品编码
	 */
	private String orgDrugCode;
	/**
	 * 院方药品名称
	 */
	private String orgDrugName;
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
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 删除标志 0 正常
	 */
	private String delFlag;
	/**
	 * 药品企业
	 */
	private String orgDrugCompany;
	/**
	 * 剂型
	 */
	private String orgDrugDosageForm;
	/**
	 * 规格
	 */
	private String orgDrugSpec;


}

