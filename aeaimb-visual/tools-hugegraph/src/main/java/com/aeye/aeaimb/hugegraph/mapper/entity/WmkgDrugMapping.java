package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医院药品映射(BaseDrugMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugMapping implements Serializable {
	private static final long serialVersionUID = -65918499389288324L;
	/**
	 * id
	 */
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


}

