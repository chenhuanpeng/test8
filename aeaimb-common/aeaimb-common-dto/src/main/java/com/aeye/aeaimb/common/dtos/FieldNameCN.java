package com.aeye.aeaimb.common.dtos;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface FieldNameCN {
    String value() ;
}