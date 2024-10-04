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
 * [问诊]字典表
 * </p>
 *
 * @author Administrator
 * @since 2024-09-05
 */
@Getter
@Setter
@TableName("cstn_dict")
public class CstnDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * dict_type
     */
    @TableField(value = "dict_type")
    private String dictType;

    /**
     * item_value
     */
    @TableField(value = "item_value")
    private String itemValue;

    /**
     * item_label
     */
    @TableField("item_label")
    private String itemLabel;

    /**
     * 排序（升序）
     */
    @TableField("sort_order")
    private Integer sortOrder;


}
