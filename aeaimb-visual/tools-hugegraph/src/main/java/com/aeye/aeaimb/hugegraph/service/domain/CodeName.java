package com.aeye.aeaimb.hugegraph.service.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 字典表(SysDict)实体类
 *
 * @author linkeke
 * @since 2024-07-19 18:42:19
 */
@Data
public class CodeName implements Serializable {
	private static final long serialVersionUID = 192780698802365290L;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 字典类型
	 */
	private String name;

}

