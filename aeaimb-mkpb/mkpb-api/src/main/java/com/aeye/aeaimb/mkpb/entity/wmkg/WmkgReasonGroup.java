package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理规则组(WmkgReasonGroup)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonGroup implements Serializable {
    private static final long serialVersionUID = -51087397762562876L;
    /**
     * 规则组ID
     */
	@TableId(type = IdType.ASSIGN_UUID)
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


}

