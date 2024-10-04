package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (KgKnowledge)实体类
 *
 * @author linkeke
 * @since 2024-08-14 09:43:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class KgKnowledge implements Serializable {
	private static final long serialVersionUID = -23576949056440776L;
	/**
	 * 知识id
	 */
	private String id;
	/**
	 * 知识类型：疾病: T1,临床路径 : T2,临床指南 : T3,病例: T4,症状: T5,药品: T6,手术: T7,检查: T8,检验: T9,操作治疗: T10,量表信息: T11,治疗方案: T12,护理方案: T13,健教知识: T14,医学文献: T15,法律法规: T16,
	 */
	private String type;
	/**
	 * 知识名称
	 */
	private String name;
	/**
	 * 主题ID
	 */
	private String kgBaseId;
	/**
	 * 主题名称
	 */
	private String kgBaseName;
	/**
	 * 当前版本
	 */
	private Integer currentVersion;
	/**
	 * 发布时间
	 */
	private Date publishDate;
	/**
	 * 是否发布
	 */
	private String publishFlag;
	/**
	 * 标准编码
	 */
	private String normCode;

	private String createBy;

	private String updateBy;

	private Date createTime;

	private Date updateTime;


}

