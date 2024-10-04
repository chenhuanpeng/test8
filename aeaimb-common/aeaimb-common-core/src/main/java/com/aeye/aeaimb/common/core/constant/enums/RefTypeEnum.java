package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefTypeEnum {

	PATH_CONDITION("1", "路径条件"),
	PURPOSE_CONDITION("2", "目标条件"),
	QUESTION_CONDITION("3", "问题条件");


	private final String value;
	private final String message;

	public static RefTypeEnum getByValue(String value) {
		for (RefTypeEnum state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
