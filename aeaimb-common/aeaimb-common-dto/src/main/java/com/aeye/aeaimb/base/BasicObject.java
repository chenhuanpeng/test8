package com.aeye.aeaimb.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用基础对象
 * @author yangjing
 * @date 2024/8/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicObject implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 编码
	 * @mock zz.890
	 */
	private String code;
	/**
	 * 名称
	 * @mock 呼吸系统
	 */
	private String name;
	/**
	 * 别名/简称
	 */
	private String alias;
	/**
	 * 父编码
 	 */
	private String parentCode;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 级别
	 */
	private String level;

	public BasicObject(String code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public BasicObject(String code, String name,String parentCode)
	{
		this.code = code;
		this.name = name;
		this.parentCode = parentCode;
	}

	public BasicObject(String code, String name,String alias,String level)
	{
		this.code = code;
		this.name = name;
		this.alias = alias;
		this.level = level;
	}
}
