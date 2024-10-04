package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationEnum {
	ADD("add", "新增"),

	COPY("copy", "复制"),
	EDIT("edit", "编辑"),
	REMOVE("remove", "删除"),
	SHOW("show", "查看"),
	PREVIEW("preview", "预览"),
	ONLINE("online", "上线"),
	OFFLINE("offline", "下线"),
	AUDIT("audit", "审核"),
	SUBMIT("submit", "提交"),
	BATCH_AUDIT("batch_audit", "批量审核"),
	BATCH_ONLINE("batch_online", "批量上线"),
	BATCH_OFFLINE("batch_offline", "批量下线"),
	RETURN_EDIT("return_edit", "退回编辑");


	private final String value;
	private final String message;

	public static OperationEnum getByValue(String value) {
		for (OperationEnum state : values()) {
			if (state.getValue().equals(value)) {
				return state;
			}
		}
		return null;
	}
}
