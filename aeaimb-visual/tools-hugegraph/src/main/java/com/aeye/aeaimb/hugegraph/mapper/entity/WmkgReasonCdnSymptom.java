package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则条件症状(WmkgReasonCdnSymptom)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnSymptom implements Serializable {
	private static final long serialVersionUID = 826297354917210986L;
	/**
	 * 条件ID
	 */
	private String cdnId;
	/**
	 * 症状编码
	 */
	private String symptomCode;
	/**
	 * 症状名称（用于显示，不关联）
	 */
	private String symptomName;
	/**
	 * 主要症状：是/否
	 */
	private String symptomMain;


}

