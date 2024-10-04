package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]人卫知识与医疗字典映射(WmkgDictKgMapping)实体类
 *
 * @author linkeke
 * @since 2024-08-14 10:06:26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDictKgMapping implements Serializable {
	private static final long serialVersionUID = -71303061134906321L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 医疗字典编码
	 */
	private String mediDictCode;
	/**
	 * 医疗字典名称
	 */
	private String mediDictName;
	/**
	 * 医疗字典类型
	 */
	private String mediDictType;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 知识ID
	 */
	private String kgId;
	/**
	 * 知识类型
	 */
	private String kgType;
	/**
	 * 知识名称
	 */
	private String kgName;
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

