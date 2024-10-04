package com.aeye.aeaimb.mkpb.vo.wmkg;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 疾病字典(BaseDiseaseDict)实体类
 *
 * @author linkeke
 * @since 2023-11-30 17:50:36
 */
@Data
public class WmkgDiseaseDictVO implements Serializable {
	private static final long serialVersionUID = -67134209139379722L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
	private String cateType;
	private String cateCode;
	private String cateName;
	/**
	 * 疾病名称
	 */
	private String diseaseName;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 排序
	 */
	private Integer sort;
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
	 * 删除标志 0 正常
	 */
	private String delFlag;


}

