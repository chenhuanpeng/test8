package com.aeye.aeaimb.mkpb.entity.wmkg;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]科室字典(WmkgDeptDict)实体类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgDeptDict implements Serializable {
    private static final long serialVersionUID = -88420719135349178L;
    /**
     * 科室编码
     */
	@TableId(value = "dept_code", type = IdType.INPUT)
    private String deptCode;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 父科室
     */
    private String parentCode;
    /**
     * 排序
     */
    private Integer sort;

}

