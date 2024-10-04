package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]同义词(WmkgSynonyms)实体类
 *
 * @author linkeke
 * @since 2024-07-17 13:33:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgSynonyms implements Serializable {
	private static final long serialVersionUID = -46362620649159633L;
	/**
	 * 同义词编码
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String synonymsCode;
	/**
	 * 同义词分类：别名、同义
	 */
	private String synonymsType;
	/**
	 * 对象编码
	 */
	private String objCode;
	/**
	 * 对象分类
	 */
	private String objType;
	/**
	 * 源名称
	 */
	private String sourceName;
	/**
	 * 目标名称
	 */
	private String targetName;


}

