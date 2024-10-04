package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医院疾病映射(BaseDiseaseMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseDiseaseMapping implements Serializable {
	private static final long serialVersionUID = 228685849376480670L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 机构ID
	 */
	private String orgId;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
	/**
	 * 疾病名称
	 */
	private String diseaseName;
	/**
	 * 疾病编码
	 */
	private String orgDiseaseCode;
	/**
	 * 疾病名称
	 */
	private String orgDiseaseName;
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
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;


}

