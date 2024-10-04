package com.aeye.aeaimb.hugegraph.controller.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 临床指南表(TcmsClinicalGuide)实体类
 *
 * @author linkeke
 * @since 2024-06-11 16:58:16
 */
@Data
public class SchemaDto implements Serializable {
    private static final long serialVersionUID = 995466553525800525L;
    /**
     * 实体
     */
    @ExcelProperty(value = "实体")
    private String vertex;
    /**
     * 属性
     */
    @ExcelProperty(value = "属性")
    private String property;

}

