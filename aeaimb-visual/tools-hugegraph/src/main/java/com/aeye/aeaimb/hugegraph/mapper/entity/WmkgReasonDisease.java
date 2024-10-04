package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理疾病(WmkgReasonDisease)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:28:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonDisease implements Serializable {
	private static final long serialVersionUID = 300433803955383877L;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
	/**
	 * 疾病名称
	 */
	private String diseaseName;
	/**
	 * 疾病排序
	 */
	private Integer diseaseSort;


}

