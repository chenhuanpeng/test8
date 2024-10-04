package com.aeye.aeaimb.mkpb.dto.wmkg;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 疾病字典(BaseDiseaseDict)实体类
 *
 * @author linkeke
 * @since 2023-11-30 17:50:36
 */
@Data
public class WmkgDiseaseDictDTO implements Serializable {
	private static final long serialVersionUID = -40173890218338187L;
	/**
	 * 疾病编码（诊断编码）
	 */
	@NotBlank(message = "疾病编码不能为空")
	@Length(min = 0,max = 50)
	private String diseaseCode;
	/**
	 * 疾病名称
	 */
	@NotBlank(message = "疾病编码不能为空")
	@Length(min = 0,max = 50)
	private String diseaseName;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 排序
	 */
	private Integer sort;

}

