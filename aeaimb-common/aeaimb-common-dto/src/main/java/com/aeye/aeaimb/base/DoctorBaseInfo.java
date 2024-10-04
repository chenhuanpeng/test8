package com.aeye.aeaimb.base;

import lombok.Data;

@Data
public class DoctorBaseInfo {
	/**
	 * [内部]医生ID
	 */
	private String doctorId;
	/**
	 * 医生名称
	 */
	private String doctorName;
	/**
	 * [外部]医生编码
	 */
	private String doctorGuid;
}
