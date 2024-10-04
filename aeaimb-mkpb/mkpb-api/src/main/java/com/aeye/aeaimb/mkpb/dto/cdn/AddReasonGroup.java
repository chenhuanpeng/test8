package com.aeye.aeaimb.mkpb.dto.cdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 推理规则组(WmkgReasonGroup)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
@Data
public class AddReasonGroup<T> implements Serializable {
    private static final long serialVersionUID = -51087397762562876L;
    /**
     * 规则组ID
     */
    private String groupId;
    /**
     * 规则组名称
     */
    private String groupName;
    /**
     * 规则组关系:and/or
     */
    private String groupCondition;
    /**
     * 规则组排序
     */
    private Integer groupSort;
    /**
     * 推理ID
     */
    private String reasonId;
    /**
     * 业务分组类型
     */
    private String factorCate;

	/**
	 * 症状规则组规则
	 */
	private List<T> reasonCdns;

}

