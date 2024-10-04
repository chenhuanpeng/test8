package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]疾病所属分类(WmkgDiseaseInCategory)实体类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiseaseInCategory implements Serializable {
    private static final long serialVersionUID = 448471622476455462L;
    /**
     * id
     */
	@TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 疾病编码
     */
    private String diseaseCode;
    /**
     * 分类编码规范
     */
    private String normType;
    /**
     * 分类编码
     */
    private String cateCode;


}

