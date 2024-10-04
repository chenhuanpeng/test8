package com.aeye.aeaimb.common.webui.validation.custom;

import org.springframework.core.annotation.AliasFor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 文件字符集验证
 */
@Documented
@Constraint(validatedBy = {TextFileValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TextFile {
    String message() default "{sanri.webui.validator.constraints.charset}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @AliasFor("charsets")
    String [] value() default {"utf-8"};
    /**
     * 支持的字符集列表，对 utf-8 的验证比较敏感，对 gbk 好像不行
     * @return
     */
    @AliasFor("value")
    String [] charsets() default {"utf-8"};

    /**
     * 字符数限制，默认不限字数
     * @return
     */
    long charsLimit() default -1;
}
