package com.aeye.aeaimb.common.webui.paramlog;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@EnableConfigurationProperties({ParamLogProperties.class})
@Import(MethodInvokeAop.class)
public class ParamLogAutoConfiguration {
}
