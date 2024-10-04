package com.aeye.aeaimb.common.webui.dto;

import lombok.Data;

import java.util.Objects;

/**
 * 字典数据
 * @param <T>
 */
@Data
public class DictDto<T> {
    private String label;
    private T value;

    public DictDto() {
    }

    public DictDto(String label, T value) {
        this.label = label;
        this.value = value;
    }

}
