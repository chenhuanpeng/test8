package com.aeye.aeaimb.mkpb.entity.wmkg;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 推理规则条件症状(WmkgReasonCdnSymptom)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WmkgReasonCdnSymptom implements Serializable {
    private static final long serialVersionUID = 543629438420274627L;
    /**
     * 条件ID
     */
	@TableId
    private String cdnId;
    /**
     * 症状编码
     */
    private String symptomCode;
    /**
     * 症状名称（用于显示，不关联）
     */
    private String symptomName;
    /**
     * 主要症状：是/否
     */
    private String symptomMain;
    private String factorWeight;
}

