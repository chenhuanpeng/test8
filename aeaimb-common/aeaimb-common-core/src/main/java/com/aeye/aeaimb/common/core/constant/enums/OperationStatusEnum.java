package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationStatusEnum {
	WAIT_SUBMIT("1", "待提交"),
	WAIT_AUDIT("2", "待审核"),
	WAIT_ONLINE("3", "待上线"),
	ALREADY_ONLINE("4", "已上线"),
	AUDIT_FAILED("5", "审核不通过"),
	ALREADY_OFFLINE("6", "已下线");


	private final String value;
	private final String message;

	public static OperationStatusEnum getByValue(String value) {
		for (OperationStatusEnum state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
