package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagDiagnosis extends Diagnosis{
	/**
	 * 标签列表
	 */
	private List<DiagTag> diagTags = new ArrayList<>();

	public TagDiagnosis() {
	}

	public TagDiagnosis(String code, String name, String desc) {
		super(code, name, desc);
	}
}
