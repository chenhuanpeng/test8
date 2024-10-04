package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 */
@Data
@Schema(description = "问诊路径展示对象")
public class PathVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	private String id;

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
	 * 使用科室
	 */
	private String department;


	/**
	 * 路径状态
	 */
	private String pathStatus;


	/**
	 * 科室ID
	 */
	private String departmentId;

	//选择条件
	List<ConditionVO> selConditionList;


}
