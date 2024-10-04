package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理疾病定义(WmkgReasonDefine)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonDefine implements Serializable {
	private static final long serialVersionUID = -64966019253132295L;
	/**
	 * 推理ID
	 */
	private String reasonId;
	/**
	 * 方案名称
	 */
	private String reasonName;
	/**
	 * 疾病名称
	 */
	private String diseaseName;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;
	/**
	 * 推理排序
	 */
	private Integer diseaseSort;
	/**
	 * 启用状态:1开0关
	 */
	private Integer reasonStatus;
	/**
	 * 金标依据比例
	 */
	private Object imptRatio;
	/**
	 * 重要依据比例
	 */
	private Object genlRatio;
	/**
	 * 一般依据比例
	 */
	private Object minoRatio;
	/**
	 * 无关依据比例
	 */
	private Object negvRatio;
	/**
	 * 负向依据比例
	 */
	private Object irreRatio;
	/**
	 * 全院/专科
	 */
	private String scopeType;
	/**
	 * 专科名称
	 */
	private String scopeValue;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 删除标志 0 正常
	 */
	private String delFlag;


}

