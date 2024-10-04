package com.aeye.aeaimb.hugegraph.controller.dto.kg;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 疾病字典(BaseDiseaseDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:39
 */
@Data
public class DiseaseDictKgMappingDto implements Serializable {
	private static final long serialVersionUID = -49378283130147871L;

	/**
	 * 疾病编码
	 */
	@ExcelProperty(value = "诊断代码")
	private String diseaseCode;
	/**
	 * 疾病名称
	 */
	@ExcelProperty(value = "诊断名称")
	private String diseaseName;
	/**
	 * 知识库疾病代码
	 */
	@ExcelProperty(value = "知识库疾病代码")
	private String id;

	/**
	 * 知识库疾病名称
	 */
	@ExcelProperty(value = "知识库疾病名称")
	private String name;
	/**
	 * 知识来源
	 */
	@ExcelProperty(value = "知识来源")
	private String resource;

	@ExcelProperty(value = "临床指南ID")
	private String  clinicalGuideID;


	@ExcelProperty(value = "临床指南名称")
	private String clinicalGuideName;

	@ExcelProperty(value = "临床路径ID")
	private String clinicalPathID;

	@ExcelProperty(value = "临床路径名称")
	private String clinicalPathName;

}

