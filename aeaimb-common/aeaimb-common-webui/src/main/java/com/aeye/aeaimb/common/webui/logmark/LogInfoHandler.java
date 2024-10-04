package com.aeye.aeaimb.common.webui.logmark;

public interface LogInfoHandler {
    void before(LogInfo logInfo);

    void around(AroundLogInfo aroundLogInfo);
}
