package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 症状字典(BaseSymptomDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:39:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgSymptomDict implements Serializable {
	private static final long serialVersionUID = 749135666710176385L;
	/**
	 * 症状编码
	 */
	private String symptomCode;
	/**
	 * 症状名称
	 */
	private String symptomName;
	/**
	 * 症状分类
	 */
	private String symptomType;
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

