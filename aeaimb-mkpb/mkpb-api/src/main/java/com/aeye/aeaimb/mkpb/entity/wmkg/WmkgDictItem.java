package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * wmkg_dict_item(WmkgDictItem)实体类
 *
 * @author linkeke
 * @since 2024-08-19 14:34:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDictItem implements Serializable {
	private static final long serialVersionUID = -17718620606976589L;
	/**
	 * 编号
	 */
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 字典ID
	 */
	private String dictId;
	/**
	 * 字典项值
	 */
	private String itemValue;

	@TableField(exist = false)
	private String value;
	/**
	 * 字典项名称
	 */
	private String label;
	/**
	 * 字典类型
	 */
	private String dictType;
	/**
	 * 字典项描述
	 */
	private String description;
	/**
	 * 排序（升序）
	 */
	private Integer sortOrder;
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
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private Date updateTime;
	/**
	 * 备注信息
	 */
	private String remarks;
	/**
	 * 删除标志
	 */
	private String delFlag;


}

