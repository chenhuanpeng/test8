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
 * [问诊]问题选项
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Getter
@Setter
@TableName("cstn_question_option")
public class CstnQuestionOption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 问题id
     */
    @TableField("qa_id")
    private String qaId;

    /**
     * 选项内容
     */
    @TableField("option_name")
    private String optionName;

    /**
     * 选项排序
     */
    @TableField("option_sort")
    private Integer optionSort;
}
