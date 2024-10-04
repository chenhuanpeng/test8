package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 手术字典(BaseOperationDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgOperationDict implements Serializable {
	private static final long serialVersionUID = 843208970497319556L;
	/**
	 * 手术编码
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
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

