package com.aeye.aeaimb.common.dtos;

import com.aeye.aeaimb.common.dtos.aggregation.TcmFourDiagQuestion;
import lombok.Data;

import java.util.List;

@Data
public class QAInfo {
	/**
	 * 问题和选择的答案项
	 */
	protected List<TcmFourDiagQuestion.QuestionItem> questionItems;
	/**
	 * 选择类型 1疾病，2主症
	 *
	 * @mock 1
	 */
	protected String selectType;

	/**
	 * 会话标识
	 * @mock 0001
	 */
	protected String sessionId;

	/**
	 * 问诊点击下一步有用到
	 * @mock next
	 */
	protected String optStep;
}
