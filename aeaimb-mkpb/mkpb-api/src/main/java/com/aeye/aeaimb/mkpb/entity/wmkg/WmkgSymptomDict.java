package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]症状字典(WmkgSymptomDict)实体类
 *
 * @author linkeke
 * @since 2024-08-24 13:38:21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgSymptomDict implements Serializable {
    private static final long serialVersionUID = 642117360052267199L;
    /**
     * 症状编码
     */
	@TableId
    private String symptomCode;
    /**
     * 症状名称
     */
    private String symptomName;
    /**
     * 症状分类
     */
    private String symptomType;
    /**
     * 创建人
     */
	@TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 修改人
     */
	@TableField(fill = FieldFill.UPDATE)
    private String updateBy;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
	@TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
    /**
     * 删除标志 0 正常
     */
    private String delFlag;


}

