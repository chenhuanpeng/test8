package com.aeye.aeaimb.mkpb.service.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * [西医]疾病(WmkgDiagnosis)实体类
 *
 * @author linkeke
 * @since 2024-06-24 13:44:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiagnosis implements Serializable {
	/**
	 * 疾病编码
	 */
	@TableField("编码")
	private String diagCode;
	/**
	 * 疾病名
	 */
	@TableField("名称")
	private String diagName;
	/**
	 * 危重标志
	 */
	@TableField("危重标志")
	private String isCriticalIllness;
	/**
	 * 主键ID
	 */
	@TableField("主键ID")
	private String id;


}

