package com.aeye.aeaimb.mkpb.vo.cstn;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author chenhuanpeng
 * @date 2024/09/02
 */
@Data
@Schema(description = "metadata展示对象")
public class MetaDataVO implements Serializable {

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


}
