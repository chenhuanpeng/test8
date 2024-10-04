package com.aeye.aeaimb.hugegraph.mapper.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 推理规则条件检验(WmkgReasonCdnLabtest)实体类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnLabtest implements Serializable {
    private static final long serialVersionUID = 591162395410029911L;
    /**
     * 条件ID
     */
    private String cdnId;
    /**
     * 检验编码
     */
    private String labtestCode;
    /**
     * 检验名称（用于显示，不关联）
     */
    private String labtestName;


}

