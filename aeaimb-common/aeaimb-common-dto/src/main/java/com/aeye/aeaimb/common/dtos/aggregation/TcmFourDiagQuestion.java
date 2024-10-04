package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.ConsulationPhysique;
import com.aeye.aeaimb.common.dtos.QAInfo;
import com.aeye.aeaimb.common.dtos.TcmbDiagInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 中医四诊之问诊
 */
@Data
public class TcmFourDiagQuestion extends QAInfo {
	/**
	 * 当前进度ID
	 * @mock 10
	 */
	private String processId;

	/**
	 * 是否已经完成问诊
	 * @mock true
	 */
	private boolean done;

	/**
	 * 症候列表
	 */
	private List<? extends TcmbDiagInfo> syndromes;

	/**
	 * 进度项
	 */
	@Data
	public static class ProcessItem{
		/**
		 * 进度ID
		 * @mock 0
		 */
		private String processId;
		/**
		 * 进度名称
		 * @mock 主诉
		 */
		private String processName;

		public ProcessItem() {
		}

		public ProcessItem(String processId, String processName) {
			this.processId = processId;
			this.processName = processName;
		}
	}

	/**
	 * 一个问题
	 */
	@Data
	public static class QuestionItem{
		/**
		 * 步骤 0 主诉,  10 刻下症, 20 体格检查, 40 其它
		 * @mock 0
		 */
		private String step;
		/**
		 * 问题ID 唯一标识一个问题
		 * @mock 001
		 */
		private String questionId;
		/**
		 * 问题标题
		 * @mock 是否有以下主症
		 */
		private String questionTitle;
		/**
		 * 顺序
		 * @mock 1
		 */
		private int sort;
		/**
		 * 选项树 用于输出到前端
		 */
		private List<QuestionOptionTree> optionTrees;

		/**
		 * 体格检查项目列表
		 */
		private List<ConsulationPhysique> consulationPhysiques;

		public QuestionItem() {
		}

		public QuestionItem(String step, String questionId, String questionTitle, int sort) {
			this.step = step;
			this.questionId = questionId;
			this.questionTitle = questionTitle;
			this.sort = sort;
		}
	}

	/**
	 * 问题选项
	 */
	@Data
	public static class QuestionOption{
		/**
		 * 选项编码 即症状编码
		 * @mock 001
		 */
		private String optionCode;
		/**
		 * 症状名称
		 * @mock 头晕
		 */
		private String optionName;
		/**
		 * 父级选项编码
		 * @mock 002
		 */
		private String parentOptionCode;
		/**
		 * 层级
		 * @mock 1
		 */
		private int level;
		/**
		 * 排它项
		 * @mock 003,004
		 */
		private List<String> exclusives;

		/**
		 * 是否选中 产品侧传给研究院
		 * @mock true
		 */
		private boolean checked;

		public QuestionOption() {
		}

		public QuestionOption(String optionCode, String optionName) {
			this.optionCode = optionCode;
			this.optionName = optionName;
		}

		public QuestionOption(String optionCode, String optionName, String parentOptionCode, int level, List<String> exclusives, boolean checked) {
			this.optionCode = optionCode;
			this.optionName = optionName;
			this.parentOptionCode = parentOptionCode;
			this.level = level;
			this.exclusives = exclusives;
			this.checked = checked;
		}
	}

	/**
	 * 问题选项树
	 */
	@Data
	public static class QuestionOptionTree {
		/**
		 * 问题选项
		 */
		private QuestionOption option;
		/**
		 * 子级问题
		 */
		private List<QuestionOptionTree> children = new ArrayList<>();

		public QuestionOptionTree() {
		}

		public QuestionOptionTree(QuestionOption option) {
			this.option = option;
		}
	}

	public Set<ProcessItem> getProcessItems() {
		return FourQuestionProcessRepo.getProcessItems();
	}

	/**
	 * 比较两颗总诊选项树的选中情况
	 * @return
	 */
	public static boolean compareTwoTree(TcmFourDiagQuestion.QuestionOptionTree left, TcmFourDiagQuestion.QuestionOptionTree right){
		TcmFourDiagQuestion.QuestionOption leftOption = left.getOption();
		TcmFourDiagQuestion.QuestionOption rightOption = right.getOption();

		boolean optionEqual = leftOption.getOptionCode().equals(rightOption.getParentOptionCode());
		boolean checkEqual = leftOption.isChecked() && rightOption.isChecked();
		if (!optionEqual || checkEqual){
			return false;
		}

		List<TcmFourDiagQuestion.QuestionOptionTree> leftChildren = left.getChildren();
		List<TcmFourDiagQuestion.QuestionOptionTree> rightChildren = right.getChildren();
		int leftChildrenSize = leftChildren == null ?  0 : leftChildren.size();
		int rightChildrenSize = rightChildren == null ? 0 : rightChildren.size();
		if (leftChildrenSize != rightChildrenSize){
			return false;
		}
		if (leftChildrenSize == 0){
			return true;
		}

		Map<String, QuestionOptionTree> optionCodeToOptionMap = leftChildren.stream().collect(Collectors.toMap(item -> item.getOption().getOptionCode(), Function.identity()));
		for (TcmFourDiagQuestion.QuestionOptionTree rightChild : rightChildren) {
			String optionCode = rightChild.getOption().getOptionCode();
			TcmFourDiagQuestion.QuestionOptionTree questionOptionTree = optionCodeToOptionMap.get(optionCode);
			if (questionOptionTree == null){
				return false;
			}
			boolean compareTwoTree = compareTwoTree(rightChild, questionOptionTree);
			if (!compareTwoTree){
				return false;
			}
		}

		return true;
	}
}
