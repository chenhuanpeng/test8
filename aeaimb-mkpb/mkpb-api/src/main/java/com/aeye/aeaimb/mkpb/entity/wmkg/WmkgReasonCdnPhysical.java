package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理规则体格检查(WmkgReasonCdnPhysical)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnPhysical implements Serializable {
    private static final long serialVersionUID = 747352633314413092L;
    /**
     * 条件ID
     */
	@TableId
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

