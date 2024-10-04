package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]药品字典(WmkgDrugDict)实体类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDrugDict implements Serializable {
	private static final long serialVersionUID = -67636290426790662L;
	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 药品编码
	 */
	private String drugCode;
	/**
	 * 药品名称
	 */
	private String drugName;
	/**
	 * 通用名称
	 */
	private String drugNameGe;
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
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 删除标志 0 正常
	 */
	private String delFlag;


}

