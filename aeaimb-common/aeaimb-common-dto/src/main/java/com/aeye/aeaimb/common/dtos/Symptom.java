package com.aeye.aeaimb.common.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Symptom{
	/**
	 * 症状编码
	 * @mock 111
	 */
	protected String sympCode;
	/**
	 * 症状名称
	 * @mock 头晕
	 */
	protected String sympName;

	/**
	 * 是否主症 1 主症, 2 兼症
	 * @mock 1
	 */
	protected String mainSymp;
	/**
	 * 部位编码
	 * @mock 001
	 */
	protected String symptomCate;

	/**
	 * 四诊类型
	 * 1 2 3 4 望 闻 问 切
	 */
	protected String symptomType;

	public Symptom() {
	}

	public Symptom(String sympCode, String sympName, String mainSymp) {
		this.sympCode = sympCode;
		this.sympName = sympName;
		this.mainSymp = mainSymp;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Symptom symptom = (Symptom) o;
		return Objects.equals(sympCode, symptom.sympCode) ||
				Objects.equals(sympName, symptom.sympName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sympCode, sympName);
	}

	/**
	 * 方剂症状匹配列表
	 */
	@Data
	public static class SymptomMatch extends Symptom {
		/**
		 * 是否匹配当前患者状态
		 */
		private boolean match;
	}

	/**
	 * 拥有数据来源的症状数据
	 */
	@Getter
	@Setter
	@ToString
	public static class SymptomSource extends Symptom{
		/**
		 * 症状来源 {main: 主选症状, question: 来源于问题, tongue: 来源于舌诊, face: 来源于而诊, his: 来源于 his, edit: 来源于编辑症状}
		 */
		private String source;

		public SymptomSource() {
		}

		public SymptomSource(Symptom symptom, String source) {
			BeanUtils.copyProperties(symptom, this);
			this.source = source;
		}
	}
}
