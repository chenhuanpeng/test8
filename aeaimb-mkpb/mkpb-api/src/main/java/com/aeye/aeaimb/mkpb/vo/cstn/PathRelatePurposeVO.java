package com.aeye.aeaimb.mkpb.vo.cstn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenhuanpeng
 * @date 2024/09/04
 */
@Data
@Schema(description = "路径关联目标VO")
public class PathRelatePurposeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 路径ID
	 */
	private String pathId;


	/**
	 * 树形结构LIST
	 */
	private List<TreeObj> data;


}
