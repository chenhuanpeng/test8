package com.aeye.aeaimb.mkpb.dto.cstn;

import com.aeye.aeaimb.common.dtos.BaseQryDto;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 * <p>
 * 目标查询传输对象
 */
@Data
@Schema(description = "目标查询传输对象")
public class CstnPurposeDTO extends BaseQryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 目标名称
	 */
	private String purposeName;

	/**
	 * 目标类型:主目标、分支目标
	 */
	private String purposeType;



}
