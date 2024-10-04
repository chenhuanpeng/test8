package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {

	AUDIT_PASS("1", "审核通过"),
	AUDIT_FAILED("2", "审核不通过");


	private final String value;
	private final String message;

	public static AuditStatusEnum getByValue(String value) {
		for (AuditStatusEnum state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
