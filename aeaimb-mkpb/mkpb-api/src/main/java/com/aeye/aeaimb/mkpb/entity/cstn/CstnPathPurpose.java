package com.aeye.aeaimb.mkpb.entity.cstn;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * [问诊]路径目标
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("cstn_path_purpose")
public class CstnPathPurpose implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 路径id
     */
    @TableField("path_id")
    private String pathId;

    /**
     * 目标id
     */
    @TableField("purpose_id")
    private String purposeId;

    /**
     * 是否循环：1是/ 0否
     */
    @TableField("loop_flag")
    private String loopFlag;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
}
