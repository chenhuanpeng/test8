package com.aeye.aeaimb.common.webui.bigfile;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启大文件上传
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BigfileConfig.class})
public @interface EnableBigFileUpload {

}
