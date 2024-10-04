package com.aeye.aeaimb.common.webui.configs;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 忽略响应值包裹
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebConfig.class})
public @interface IgnoreWrapper {
}
