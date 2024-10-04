package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典项(SysDictItem)实体类
 *
 * @author linkeke
 * @since 2024-07-19 18:42:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDictItem implements Serializable {
	private static final long serialVersionUID = -57232653054928810L;
	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 字典ID
	 */
	private String dictId;
	/**
	 * 字典项值
	 */
	private String itemValue;
	/**
	 * 字典项名称
	 */
	private String label;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 字典项描述
	 */
	private String description;
	/**
	 * 排序（升序）
	 */
	private Integer sortOrder;
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
	 * 删除标志
	 */
	private String delFlag;


}

