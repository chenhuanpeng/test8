package com.aeye.aeaimb.common.webui.configs;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebConfig.class})
public @interface EnableWebUI {
}
