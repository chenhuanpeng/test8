package com.aeye.aeaimb.mkpb.vo.wmkg;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典项(SysDictItem)实体类
 *
 * @author linkeke
 * @since 2024-07-19 18:42:20
 */
@Data
public class DictItemInfo implements Serializable {
	private static final long serialVersionUID = -57232653054928810L;
	/**
	 * 编号
	 */
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

}

