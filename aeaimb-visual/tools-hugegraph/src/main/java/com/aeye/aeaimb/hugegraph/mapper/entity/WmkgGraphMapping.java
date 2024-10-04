package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 图谱标准字典映射(BaseGraphMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgGraphMapping implements Serializable {
	private static final long serialVersionUID = 612853583374056390L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 图谱编码
	 */
	private String graphCode;
	/**
	 * 图谱名称
	 */
	private String graphName;
	/**
	 * 标准编码
	 */
	private String standCode;
	/**
	 * 标准名称
	 */
	private String standName;
	/**
	 * 字典分类: 疾病、手术、检查、检验、药品
	 */
	private String mediDictType;


}

