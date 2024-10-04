package com.aeye.aeaimb.common.webui.dto;

import lombok.Data;

/**
 * 用于解决使用通用返回导致 string 类型返回失败问题
 * @param <T>
 */
@Data
public class ValueDto<T> {
	T data;

	public ValueDto(T data) {
		this.data = data;
	}

	public ValueDto() {
	}
}
