package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 科室字典(BaseDeptDict)实体类
 *
 * @author linkeke
 * @since 2024-07-21 11:32:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDeptDict implements Serializable {
	private static final long serialVersionUID = 215454561566358457L;
	/**
	 * 科室编码
	 */
	@TableId
	private String deptCode;
	/**
	 * 科室名称
	 */
	private String deptName;
	/**
	 * 父科室
	 */
	private String parentCode;
	/**
	 * 排序
	 */
	private Integer sort;


}

