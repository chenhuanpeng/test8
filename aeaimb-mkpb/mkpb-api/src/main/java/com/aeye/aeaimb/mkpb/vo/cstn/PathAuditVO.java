package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenhuanpeng
 * @date 2024/09/03
 */
@Data
@Schema(description = "路径审核VO")
public class PathAuditVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 审核状态 1审核通过 2审核不通过
	 */
	private String auditStatus;

	/**
	 * 内容(备注)
	 */
	private String content;

	private String[] ids;



}
