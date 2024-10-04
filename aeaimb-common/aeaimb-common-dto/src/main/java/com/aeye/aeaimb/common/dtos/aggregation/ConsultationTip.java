package com.aeye.aeaimb.common.dtos.aggregation;

import com.aeye.aeaimb.common.dtos.Medical;
import com.aeye.aeaimb.common.dtos.PublicParam;
import lombok.Data;

@Data
public class ConsultationTip {
    /**
     * 公共参数
     */
    private PublicParam publicParam;
    /**
     * 病历
     */
    private Medical medical;
}
