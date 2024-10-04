package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OperatorCheck {
    /**
     * 操作治疗编码
     * @mock xxxx
     */
    private String osCode;
    /**
     * 操作治疗名称
     * @mock 雾化吸入治疗
     */
    private String osName;
    /**
     * 合理性
     */
    private List<Rationality> rationalities = new ArrayList<>();

	public OperatorCheck(){

	}

	public OperatorCheck(String osCode, String osName, List<Rationality> rationalities) {
		this.osCode = osCode;
		this.osName = osName;
		this.rationalities = rationalities;
	}
}
