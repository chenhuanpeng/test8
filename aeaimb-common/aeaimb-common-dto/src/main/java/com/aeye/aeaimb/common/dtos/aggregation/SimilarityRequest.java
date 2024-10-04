package com.aeye.aeaimb.common.dtos.aggregation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimilarityRequest extends ConsultationTip{
    /**
     * 计算阈值
     * @mock 9
     */
    private Double threshold;
}
