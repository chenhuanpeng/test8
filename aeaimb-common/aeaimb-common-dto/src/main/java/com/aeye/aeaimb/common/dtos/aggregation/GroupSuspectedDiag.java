package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.SuspectedDiagnosis;
import lombok.Data;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupSuspectedDiag {
	/**
	 * 疑似诊断分组类型
	 */
	private String groupType;
	/**
	 * 分组名称
	 */
	private String groupName;
	/**
	 * 疑似诊断列表
	 */
	@Delegate
	private List<SuspectedDiagnosis> suspectedDiags = new ArrayList<>();

	public GroupSuspectedDiag() {
	}

	public GroupSuspectedDiag(String groupType, String groupName) {
		this.groupType = groupType;
		this.groupName = groupName;
	}
}
