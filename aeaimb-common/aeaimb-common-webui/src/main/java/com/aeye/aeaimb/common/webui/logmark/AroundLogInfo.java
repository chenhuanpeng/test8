package com.aeye.aeaimb.common.webui.logmark;

import lombok.Getter;

import java.time.Duration;

@Getter
public class AroundLogInfo  {
    private LogInfo logInfo;
    /**
     * 执行时间
     */
    private Duration duration;

    /**
     * 执行结果
     */
    Object result;

    public AroundLogInfo(LogInfo logInfo, Duration duration, Object result) {
        this.logInfo = logInfo;
        this.duration = duration;
        this.result = result;
    }
}
