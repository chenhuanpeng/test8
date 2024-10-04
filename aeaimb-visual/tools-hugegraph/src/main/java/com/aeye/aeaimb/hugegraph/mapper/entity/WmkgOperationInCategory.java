package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 手术所属分类(BaseOperationInCategory)实体类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgOperationInCategory implements Serializable {
	private static final long serialVersionUID = -93540895117987513L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 疾病编码
	 */
	private String operationCode;
	/**
	 * 分类编码规范
	 */
	private String normType;
	/**
	 * 分类编码
	 */
	private String cateCode;


}

