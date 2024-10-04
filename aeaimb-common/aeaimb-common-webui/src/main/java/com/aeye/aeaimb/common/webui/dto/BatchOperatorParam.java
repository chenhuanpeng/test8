package com.aeye.aeaimb.common.webui.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量操作输入
 * @param <T>
 */
@Data
public class BatchOperatorParam<T> {
    private List<String> ids = new ArrayList<>();
    private T param;

    public BatchOperatorParam() {
    }

    public BatchOperatorParam(List<String> ids, T param) {
        this.ids = ids;
        this.param = param;
    }
}
