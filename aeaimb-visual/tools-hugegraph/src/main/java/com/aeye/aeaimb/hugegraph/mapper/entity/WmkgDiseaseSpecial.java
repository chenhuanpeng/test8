package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 疾病特殊处理建议(WmkgDiseaseSpecial)实体类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiseaseSpecial implements Serializable {
    private static final long serialVersionUID = -22311937260721949L;
    /**
     * 编号
     */
	@TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 疾病编码
     */
    private String diseaseCode;
    /**
     * 疾病处置说明
     */
    private String diseaseDisposal;
	/**
	 * 疾病标签
	 */
    private String diseaseLabel;
    /**
     * 排序
     */
    private Integer diseaseSort;


}

