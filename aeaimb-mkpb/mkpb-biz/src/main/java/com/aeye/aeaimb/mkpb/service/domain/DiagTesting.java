package com.aeye.aeaimb.mkpb.service.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * [西医]疾病(WmkgDiagnosis)实体类
 *
 * @author linkeke
 * @since 2024-06-24 13:44:42
 */
@Data
public class DiagTesting implements Serializable {
	/**
	 * 疾病编码
	 */
	@TableField("编码")
	private String testingCode;
	/**
	 * 疾病名
	 */
	@TableField("名称")
	private String testingName;
	/**
	 * 注意事项
	 */
	@TableField("注意事项")
	private String notes;
	/**
	 * 主键ID
	 */
	@TableField("主键ID")
	private String id;


}

