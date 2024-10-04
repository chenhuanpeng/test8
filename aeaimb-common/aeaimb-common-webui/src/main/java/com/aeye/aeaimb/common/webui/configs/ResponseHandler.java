package com.aeye.aeaimb.common.webui.configs;


import com.aeye.aeaimb.common.core.util.R;

public interface ResponseHandler<T> {
    /**
     * 处理输出结果
     * @param responseDto
     * @return
     */
    T handlerOut(R responseDto);

    /**
     * 处理异常结果
     * @param responseDto
     * @return
     */
    T handlerError(R responseDto);
}
