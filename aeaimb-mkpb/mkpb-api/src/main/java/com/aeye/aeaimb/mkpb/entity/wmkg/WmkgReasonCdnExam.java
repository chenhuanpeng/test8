package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理规则条件检查(WmkgReasonCdnExam)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnExam implements Serializable {
	private static final long serialVersionUID = 176313673583712207L;
	/**
	 * 条件ID
	 */
	@TableId
	private String cdnId;
	/**
	 * 检查编码
	 */
	private String examCode;
	/**
	 * 检查名称（用于显示，不关联）
	 */
	private String examName;
	/**
	 * 影像所见
	 */
	private String examFindings;
	/**
	 * 影像结论
	 */
	private String examConclusions;


}

