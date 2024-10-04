package com.aeye.aeaimb.mkpb.dto.cstn;

import com.aeye.aeaimb.common.dtos.BaseQryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 * <p>
 * 查询传输对象
 */
@Data
@Schema(description = "查询传输对象")
public class MetaDataDTO extends BaseQryDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 元数据名称
	 */
	private String metaName;

	/**
	 * 元数据编码
	 */
	private String metaCode;

	/**
	 * 元数据层级：1级，2级，3级
	 */
	private String metaLvl;

	/**
	 * 元数据父级
	 */
	private String parentMeta;

	private String metaDict;

	private String metaDataType;

	private String unit;

}
