package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BloodUseCheck {
	/**
	 * 用血信息
	 */
	private BloodUse bloodUse;
	/**
	 * 合理性
	 */
	private List<Rationality> rationalities = new ArrayList<>();
}
