package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [西医]服务项目字典(WmkgServiceDict)实体类
 *
 * @author linkeke
 * @since 2024-07-22 11:54:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgServiceDict implements Serializable {
    private static final long serialVersionUID = -46024739389461794L;
    /**
     * 服务项目编码
     */
    private String serviceCode;
    /**
     * 服务项目名称
     */
    private String serviceName;
    /**
     * 项目类别
     */
    private String serviceType;
	/**
	 * 基础项目：1是、0否
	 */
    private String serviceBasic;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标志 0 正常
     */
    private String delFlag;


}

