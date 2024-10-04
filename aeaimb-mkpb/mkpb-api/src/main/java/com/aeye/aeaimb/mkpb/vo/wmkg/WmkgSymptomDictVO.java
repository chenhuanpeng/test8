package com.aeye.aeaimb.mkpb.vo.wmkg;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 症状字典(BaseSymptomDict)实体类
 *
 * @author linkeke
 * @since 2023-12-12 14:56:54
 */
@Data
public class WmkgSymptomDictVO implements Serializable {
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
	 * 知识ID
	 */
	private String kgId;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 是否被映射，1已映射 0未映射
	 */
	private String mappingFlag;
}

