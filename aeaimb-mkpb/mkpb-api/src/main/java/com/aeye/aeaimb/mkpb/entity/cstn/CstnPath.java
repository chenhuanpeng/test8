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
import org.springframework.data.annotation.Transient;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * [问诊]路径
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("cstn_path")
public class CstnPath implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 路径名称
     */
    @TableField("path_name")
    private String pathName;

    /**
     * 路径类型：预问诊、分导诊、辅助诊疗
     */
    @TableField("path_type")
    private String pathType;

    /**
     * 就诊类型：1初诊、2复诊
     */
    @TableField("visit_type")
    private String visitType;

    /**
     * 路径状态
     */
    @TableField("path_status")
    private String pathStatus;

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
