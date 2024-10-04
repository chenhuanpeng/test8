package com.aeye.aeaimb.common.dtos;

import com.aeye.aeaimb.base.BasicLabel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.List;

/**
 * 特殊疾病处置建议
 */
@Data
public class DisposalAdvice extends TagDiagnosis{

	public DisposalAdvice() {
	}

	public DisposalAdvice(String code, String name, String desc) {
		super(code, name, desc);
	}

	/**
	 * 处置建议列表
	 */
	private List<BasicLabel> advices;
	/**
	 * 疾病嘱托列表
	 */
	private List<String> entrusts;
}
