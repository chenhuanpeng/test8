package com.aeye.aeaimb.common.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyndromeExtend extends TcmbDiagInfo{
	protected String diagramsFlag;
	/**
	 * 是否分值很低的症候
	 */
	protected boolean lowerScore;
}
