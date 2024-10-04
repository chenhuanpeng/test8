package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医院手术映射(BaseOperationMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseOperationMapping implements Serializable {
	private static final long serialVersionUID = -32941809236729929L;
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
	 * 手术编码
	 */
	private String operationCode;
	/**
	 * 手术名称
	 */
	private String operationName;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 院方手术编码
	 */
	private String orgOperationCode;
	/**
	 * 院方手术名称
	 */
	private String orgOperationName;
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

