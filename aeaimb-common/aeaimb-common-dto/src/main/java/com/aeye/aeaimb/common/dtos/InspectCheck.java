package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InspectCheck {
    /**
     *检查检验编码
     */
    private String code;
    /**
     * 检查检验名称
     */
    private String name;
	/**
	 * 描述
	 */
	private String desc;
    /**
     * 检查/检验类型
	 * 1: 检验 , 2 : 检查
     */
    private String type;
    /**
     * 合理性
     */
    private List<Rationality> rationalities = new ArrayList<>();

	public InspectCheck() {
	}

	public InspectCheck(String code, String name, String desc, String type, List<Rationality> rationalities) {
		this.code = code;
		this.name = name;
		this.desc = desc;
		this.type = type;
		this.rationalities = rationalities;
	}


}
