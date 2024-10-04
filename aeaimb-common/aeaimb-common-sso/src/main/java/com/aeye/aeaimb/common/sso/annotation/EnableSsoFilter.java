package com.aeye.aeaimb.common.sso.annotation;

import com.aeye.aeaimb.common.sso.configuration.SsoFilterAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SsoFilterAutoConfiguration.class)
public @interface EnableSsoFilter {
}
