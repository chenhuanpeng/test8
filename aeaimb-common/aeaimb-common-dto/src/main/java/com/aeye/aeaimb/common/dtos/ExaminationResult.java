package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 检查单结果
 */
@Data
public class ExaminationResult {
	/**
	 * 检查单流水号
	 */
	private String examinationNumber;
	/**
	 * 检查单编号
	 * @mock P0230610301562
	 */
	private String examinationCode;
	/**
	 * 检查单类型
	 */
	private String examinationType;
	/**
	 * 检查单名称
	 * @mock 增强放射腹+盆CT
	 */
	private String examinationName;
	/**
	 * 检查结果
	 * @mock 子宫强化不均，双侧附件区低密度灶。盆腔少量积液。请结合临床及其他相关检查，随诊。
	 */
	private String examinationResult;
	/**
	 * 检查描述
	 * @mock 未见阳性结石影，未见异常钙化影。膀胱形态正常，膀胱壁光整，未见异常密度。腹膜后未见异常增大的淋巴结影。子宫强化明显不均，盆腔少量积液，双侧附件区见类圆形低密度影，边界清，增强扫描未见明显异常强化。
	 */
	private String examinationDesc;
	/**
	 * 检查结果产生时间
	 * @mock 2023-06-12 12:12:12
	 */
	private Date recordTime;

	/**
	 * 检查部位
	 */
	private String testPosition;
	/**
	 * 检查文件, 如拍片
	 */
	private List<File> files = new ArrayList<>();

	/**
	 * 作废：0
	 * 正常：1（状态节点不传或者传1均认为是正常数据）
	 */
	private Integer state;

	@Data
	public static final class File{
		/**
		 * 文件字节数据
		 */
		private byte[] file;
	}
	/**
	 * 拼接影像检查数据
	 * @return
	 */
	public String combineText(){
		return examinationName + "：" + examinationDesc + " " + examinationResult;
	}
}
