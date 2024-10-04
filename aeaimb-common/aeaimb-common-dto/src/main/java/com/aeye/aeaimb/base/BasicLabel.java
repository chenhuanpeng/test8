package com.aeye.aeaimb.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 标签基类
 * @author yangjing
 * @date 2024/9/21
 */
@Data
@NoArgsConstructor
public class BasicLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标签编码
	 */
	private String code;
	/**
	 * 标签名称
	 */
	private String label;
	/**
	 * 标签说明
	 */
	private String describe;

	public BasicLabel(String label,String describe){
		this.label = label;
		this.describe = describe;
	}
}
