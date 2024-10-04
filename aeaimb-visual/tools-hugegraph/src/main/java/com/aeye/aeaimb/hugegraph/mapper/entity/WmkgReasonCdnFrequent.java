package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则条件多发(WmkgReasonCdnFrequent)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnFrequent implements Serializable {
	private static final long serialVersionUID = -23305131360417838L;
	/**
	 * 条件ID
	 */
	private String cdnId;
	/**
	 * 因子编码
	 */
	private String factorType;
	/**
	 * 因子名称（用于显示，不关联）
	 */
	private String factorName;
	/**
	 * 因子值
	 */
	private String factorValue;


}

