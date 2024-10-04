package com.aeye.aeaimb.mkpb.entity.cstn;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * [问诊]历史记录表
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("cstn_operation_history")
public class CstnOperationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 外键ID
     */
    @TableField("ref_id")
    private String refId;

    /**
     * 外键类型：目标 purpose、问题question 路径 path
     */
    @TableField("ref_type")
    private String refType;

    /**
     * 操作动作 见OperationEnum枚举
     */
    @TableField("op_action")
    private String opAction;

    /**
     * 操作内容
     */
    @TableField("op_content")
    private String opContent;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 修改人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标志 0 正常
     */
    @TableField("del_flag")
    private String delFlag;
}
