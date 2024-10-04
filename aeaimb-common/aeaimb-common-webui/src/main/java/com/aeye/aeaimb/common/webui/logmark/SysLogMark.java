package com.aeye.aeaimb.common.webui.logmark;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLogMark {
//    /**
//     * 日志记录实现类
//     * 默认使用 {@link com.sanri.web.logmark.Slf4jLogInfoHandler}
//     * @return
//     */
//    String value() default "";
}
