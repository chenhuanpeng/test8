package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理检查(WmkgReasonExam)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonExam implements Serializable {
	private static final long serialVersionUID = -99821620631066934L;
	/**
	 * 检查编码
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String examCode;
	/**
	 * 检查名称
	 */
	private String examName;


}

