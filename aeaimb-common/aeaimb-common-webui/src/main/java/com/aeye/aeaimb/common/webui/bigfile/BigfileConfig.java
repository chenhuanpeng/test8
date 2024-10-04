package com.aeye.aeaimb.common.webui.bigfile;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BigfileConfig {

    @Bean
    public BigFileUploadController bigFileUploadController(){
        return new BigFileUploadController();
    }

    @ConditionalOnMissingBean(BigFileStorage.class)
    @Bean
    public BigFileStorage bigFileStorage(){
        return new LocalBigFileStorage();
    }
}
