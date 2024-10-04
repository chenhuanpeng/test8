package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenhuanpeng
 * @date 2024/09/05
 */
@Data
@Schema(description = "查表通用返回对象")
public class CommonVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String k;


	/**
	 *
	 */
	private String v;


}
