package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 运算符枚举
 */
@Getter
@AllArgsConstructor
public enum OpEnum {
	LESS_THAN("<", "小于"),
	GREATER_THAN(">", "大于"),
	//等于任意一个
	IN("in", ":"),
	EQ("==", "等于"),
	NOT_EQ("!=", "不等于"),
	GREATER_THAN_EQ(">=", "大于等于"),
	LESS_THAN_EQ("<=", "小于等于"),
	BETWEEN("between", "介于范围"),
	NOT_BETWEEN("notbetween", "介于范围外");


	private final String value;
	private final String message;

	public static OpEnum getByValue(String value) {
		for (OpEnum state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
