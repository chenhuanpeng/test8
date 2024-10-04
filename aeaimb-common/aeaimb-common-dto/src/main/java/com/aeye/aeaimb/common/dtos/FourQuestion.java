package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.List;

/**
 * 中医四诊问题
 */
@Data
public class FourQuestion {
	/**
	 * 阶段
	 * @mock 刻下症
	 */
	private String step;
	/**
	 * 问题标题
	 * @mock 主诉1：是否有以下主症？
	 */
	private String title;
	/**
	 * @mock 1
	 */
	private Integer sort;
	/**
	 * 是否完成
	 * @mock false
	 */
	private Boolean done;
	/**
	 * 选项列表
	 */
	private List<Option> options;

	/**
	 * 选项列表
	 */
	@Data
	public static class Option{
		/**
		 * 选项编码
		 * @mock 111
		 */
		private String optionCode;
		/**
		 * 选项名称
		 * @mock 血压高
		 */
		private String optionName;
		/**
		 * 父级选项
		 * @mock ""
		 */
		private String parentOptionCode;
		/**
		 * 层级
		 * @mock 1
		 */
		private Integer level;
		/**
		 * 互斥选项
		 * @mock 2
		 */
		private List<String> exclusive;
		/**
		 * 体格检查列表
		 */
		private List<Physique> physiques;
	}

	/**
	 * 体格检查项
	 */
	@Data
	public static class Physique{
		/**
		 * 检查项编码
		 * @mock 111
		 */
		private String code;
		/**
		 * 检查项名称
		 * @mock 眼部检查
		 */
		private String name;
		/**
		 * 检查项详情
		 * @mock  检查眼睑：检查眼睑的开合情况、颜色、形态等，以评估眼部肌肉和神经的功能状况。
		 */
		private String desc;
	}

	/**
	 * 四诊问题答案
	 */
	@Data
	public static class Fourqa{
		/**
		 * 阶段
		 * @mock 刻下症
		 */
		private String step;
		/**
		 * 问题标题
		 * @mock 主诉1：是否有以下主症？
		 */
		private String title;
		/**
		 * 答案列表
		 */
		private List<Answer> answers;
	}

	/**
	 * 问题答案
	 */
	@Data
	public static class Answer{
		/**
		 * 答案编码
		 * @mock 111
		 */
		private String code;
		/**
		 * 答案名称
		 * @mock 血压偏高
		 */
		private String name;
	}
}
