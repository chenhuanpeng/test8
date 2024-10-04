package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理规则条件检验(WmkgReasonCdnLabtest)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnLabtest implements Serializable {
	private static final long serialVersionUID = -53666830316254196L;
	/**
	 * 条件ID
	 */
	@TableId
	private String cdnId;
	/**
	 * 检验编码
	 */
	private String labtestCode;
	/**
	 * 检验名称（用于显示，不关联）
	 */
	private String labtestName;


}

