package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConditionTypeEnum {

	SEL_CONDITION("1", "选择条件"),
	SKIP_CONDITION("2", "跳过条件");


	private final String value;
	private final String message;

	public static ConditionTypeEnum getByValue(String value) {
		for (ConditionTypeEnum state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
