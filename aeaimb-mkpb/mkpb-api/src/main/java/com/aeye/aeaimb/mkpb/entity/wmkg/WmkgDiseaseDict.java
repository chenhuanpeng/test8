package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]疾病字典(WmkgDiseaseDict)实体类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiseaseDict implements Serializable {
	private static final long serialVersionUID = -67134209139379722L;
	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
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
	@TableField(fill = FieldFill.INSERT)
	private String createBy;
	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateBy;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;
	/**
	 * 删除标志 0 正常
	 */
	private String delFlag;


}

