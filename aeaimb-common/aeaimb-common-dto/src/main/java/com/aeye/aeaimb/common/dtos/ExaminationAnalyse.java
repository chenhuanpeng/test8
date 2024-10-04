package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class ExaminationAnalyse {
    /**
     * 检查项编码
     * @mock 00023
     */
    private String eraCode;
    /**
     * 检查项名称
     * @mock 肝胆B超
     */
    private String eraName;
    /**
     * 解读内容说明
     * @mock 解读内容说明
     */
    private String eraDesc;
}
