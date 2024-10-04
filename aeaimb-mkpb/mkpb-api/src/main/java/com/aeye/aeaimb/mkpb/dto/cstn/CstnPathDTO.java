package com.aeye.aeaimb.mkpb.dto.cstn;

import com.aeye.aeaimb.common.dtos.BaseQryDto;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 * <p>
 * 路径查询传输对象
 */
@Data
@Schema(description = "路径查询传输对象")
public class CstnPathDTO extends BaseQryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 路径名称
	 */
	private String pathName;

	/**
	 * 路径类型：预问诊、分导诊、辅助诊疗
	 */
	private String pathType;

	/**
	 * 就诊类型：1初诊、2复诊
	 */
	private String visitType;

	/**
	 * 路径状态
	 */
	private String pathStatus;

	/**
	 * 使用科室
	 */
	private String departmentId;

}
