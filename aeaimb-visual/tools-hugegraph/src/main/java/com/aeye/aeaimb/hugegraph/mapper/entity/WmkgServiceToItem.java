package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]服务项目包括指标(WmkgServiceToItem)实体类
 *
 * @author linkeke
 * @since 2024-09-06 11:09:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgServiceToItem implements Serializable {
    private static final long serialVersionUID = -74586085707668072L;
    /**
     * id
     */
	@TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 项目编码
     */
    private String serviceCode;
    /**
     * 指标代码
     */
    private String itemCode;
    /**
     * 排序
     */
    private Integer sort;


}

