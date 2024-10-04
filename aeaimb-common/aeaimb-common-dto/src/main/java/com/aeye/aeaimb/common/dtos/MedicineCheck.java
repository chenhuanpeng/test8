package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MedicineCheck {
    private String drugCode;
    private String drugName;
    private List<Rationality> rationalities = new ArrayList<>();

	public MedicineCheck() {
	}

	public MedicineCheck(String drugCode, String drugName, List<Rationality> rationalities) {
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.rationalities = rationalities;
	}
}
