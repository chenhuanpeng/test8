package com.aeye.aeaimb.common.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Setter
@Getter
@ToString
public class TcmbDiagInfo {
	/**
	 * 诊断编码
	 * @mock 001
	 */
	protected String code;
	/**
	 * 诊断名称
	 * @mock 阴阳两虚症
	 */
	protected String name;
	/**
	 * 诊断类型 2、中医诊断、3、中医证候
	 * @mock 3
	 */
	protected String diagnosisType;

	public TcmbDiagInfo() {
	}

	public TcmbDiagInfo(String code, String name, String diagnosisType) {
		this.code = code;
		this.name = name;
		this.diagnosisType = diagnosisType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TcmbDiagInfo that = (TcmbDiagInfo) o;
		return Objects.equals(code, that.code) &&
				Objects.equals(name, that.name) &&
				Objects.equals(diagnosisType, that.diagnosisType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, name, diagnosisType);
	}
}
