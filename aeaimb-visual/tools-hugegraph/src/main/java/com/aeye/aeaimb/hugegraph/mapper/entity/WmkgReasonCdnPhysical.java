package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则体格检查(WmkgReasonCdnPhysical)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnPhysical implements Serializable {
    private static final long serialVersionUID = 470901996651466882L;
    /**
     * 条件ID
     */
    private String cdnId;
    /**
     * 因子编码
     */
    private String factorType;
    /**
     * 因子名称（用于显示，不关联）
     */
    private String factorName;
    /**
     * 因子值
     */
    private String factorValue;


}

