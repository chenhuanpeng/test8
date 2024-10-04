package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 疾病所属系统表(WmkgDiseaseSystem)实体类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDiseaseSystem implements Serializable {
    private static final long serialVersionUID = -60154837076086813L;
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
     * 疾病系统
     */
    private String diseaseSystem;
    /**
     * 排序
     */
    private Integer diseaseSort;


}

