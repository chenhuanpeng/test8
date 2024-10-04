package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 症状字典映射(BaseSymptomMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:39:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgSymptomMapping implements Serializable {
	private static final long serialVersionUID = -73631197566149867L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 机构ID
	 */
	private String orgId;
	/**
	 * 症状编码
	 */
	private String symptomCode;
	/**
	 * 症状名称
	 */
	private String symptomName;
	/**
	 * 院方症状编码
	 */
	private String orgSymptomCode;
	/**
	 * 院方症状名称
	 */
	private String orgSymptomName;
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


}

