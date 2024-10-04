package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典表(SysDict)实体类
 *
 * @author linkeke
 * @since 2024-07-19 18:42:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDict implements Serializable {
	private static final long serialVersionUID = 192780698802365290L;
	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 描述
	 */
	private String description;
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
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注信息
	 */
	private String remarks;
	/**
	 * 系统标志
	 */
	private String systemFlag;
	/**
	 * 删除标志
	 */
	private String delFlag;


}

