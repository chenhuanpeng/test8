package com.aeye.aeaimb.hugegraph.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 医疗医保编码映射(BaseMedInsurMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgMedInsurMapping implements Serializable {
	private static final long serialVersionUID = 945614927136483173L;
	/**
	 * id
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String id;
	/**
	 * 医疗编码
	 */
	private String medCode;
	/**
	 * 医疗名称
	 */
	private String medName;
	/**
	 * 医疗编码分类
	 */
	private String medNormType;
	/**
	 * 医保编码
	 */
	private String insurCode;
	/**
	 * 医保名称
	 */
	private String insurName;
	/**
	 * 医保编码分类
	 */
	private String insurNormType;


}

