package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 医院服务项目映射(BaseServiceMapping)实体类
 *
 * @author linkeke
 * @since 2024-07-22 17:16:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseServiceMapping implements Serializable {
    private static final long serialVersionUID = 692078234018819423L;
    /**
     * id
     */
	@TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 机构ID
     */
    private String orgId;
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
     * 院方项目编码
     */
    private String orgServiceCode;
    /**
     * 院方项目名称
     */
    private String orgServiceName;
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


}

