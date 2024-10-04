package com.aeye.aeaimb.mkpb.dto.wmkg;

import lombok.Data;

import java.io.Serializable;

/**
 * 症状字典(BaseSymptomDict)实体类
 *
 * @author linkeke
 * @since 2023-12-12 14:56:54
 */
@Data
public class WmkgSymptomDictDTO implements Serializable {
	private static final long serialVersionUID = 981693756562192611L;
	/**
	 * 症状编码
	 */
	private String symptomCode;
	/**
	 * 症状名称
	 */
	private String symptomName;
	/**
	 * 症状分类
	 */
	private String symptomType;

	/**
	 * 新增或更新标志，1代表新增，0代表更新
	 */
	private String addFlag;

}

