package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 手术方案合理性
 */
@Data
public class SurgeryCheck {

	/**
	 * 手术列表
	 */
	private SurgerySchema.Surgery surgery;

	/**
	 * 合理性
	 */
	private List<Rationality> rationalities = new ArrayList<>();
}
