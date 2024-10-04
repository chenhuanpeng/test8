package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 病例
 */
@Data
public class ProgressNote {
	/**
	 * 文书唯一标识
	 * @mock xxxxxxxx
	 */
	private String progressGuid;
	/**
	 * 0 门诊病历
	 * 16 急诊病历
	 * 72 急诊留抢记录
	 * 73 急诊首次病程
	 * 74 急诊病程记录
	 * 75 急诊出观记录
	 * 8080 门诊复诊病历
	 * 8081 门诊补充续打病历
	 * 8082 门诊配药记录
	 * 8083 门诊手术记录
	 *
	 * @mock 0
	 */
	private String progressType;
	/**
	 * 文书类型名称
	 * @mock 门诊
	 */
	private String progressTypeName;
	/**
	 * 文书标题名称
	 * @mock 特需门诊复诊病历
	 */
	private String progressTitleName;
	/**
	 * 0: text,通过 progressMessage传递文本内容
	 * 1: xml，通过 progressMessage传递xml内容
	 * 2：map，通过messageList传递map内容
	 * 为保证文书的完整性，优先建议使用0或者1方式传递全量文书文本信息
	 * @mock 2
	 */
	private String msgType;
	/**
	 * 文书文本信息
	 * @mock 病历文书内容
	 */
	private String progressMessage;
	/**
	 * 医生工号
	 * @mock 0169
	 */
	private String doctorGuid;
	/**
	 * 医生姓名
	 * @mock 张三
	 */
	private String doctorName;
	/**
	 * 文书创建时间
	 * @mock 2023-09-27 00:08:00
	 */
	private Date recordTime;
	/**
	 * 文书状态
	 * 1.已保存 2.已提交 3.已审核4 未保存。 9. 已删除 。
	 * @mock 1
	 */
	private String progressStatus;
	/**
	 * 文书节点信息
	 */
	private List<NoteItem> messageList;

	@Data
	public static class NoteItem{
		private String key;
		private String value;
	}
}
