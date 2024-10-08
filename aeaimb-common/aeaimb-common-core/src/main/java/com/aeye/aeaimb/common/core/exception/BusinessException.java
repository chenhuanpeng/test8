package com.aeye.aeaimb.common.core.exception;


import com.aeye.aeaimb.common.core.util.R;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * 系统业务异常(根异常),异常号段为 :
 * 0 : 成功
 * 1 ~ 9999 内定系统异常段
 * 10000 ~ 99999 自定义异常码段
 * 100000 ~ Integer.MAX_VALUE 动态异常码段
 */
public class BusinessException extends RuntimeException {
    protected R responseDto;
    protected static final int  MIN_AUTO_CODE = 100000;

    public static BusinessException create(String message) {
        int value= (int) (MIN_AUTO_CODE + Math.round((Integer.MAX_VALUE - MIN_AUTO_CODE) * Math.random()));
        return create(value + "",message);
    }

    public static BusinessException create(String returnCode, String message){
        if(StringUtils.isBlank(returnCode)){
            return create(message);
        }
         BusinessException businessException = new BusinessException();
         businessException.responseDto = R.err(returnCode).msg(message);
         return businessException;
    }

    public static BusinessException create(ExceptionCause exceptionCause , Object...args){
        R responseDto = exceptionCause.result();
        String message = responseDto.getMsg();
        String formatMessage = message;

        if(ArrayUtils.isNotEmpty(args)){
            MessageFormat messageFormat = exceptionCause.getMessageFormat();
            // 判断参数是否足够，如不足够，在后面补充空字符串
            String [] argsStringArray = new String [messageFormat.getFormats().length];
            int argIndex=0;
            for (argIndex=0;argIndex<args.length;argIndex++) {
                Object arg = args[argIndex];
                argsStringArray[argIndex] = ObjectUtils.toString(arg);
            }
            for (;argIndex<argsStringArray.length;argIndex++){
                argsStringArray[argIndex] = "";
            }
            formatMessage = messageFormat.format(message,argsStringArray);
        }

        BusinessException businessException = new BusinessException();
        businessException.responseDto = R.err(responseDto.getCode()).msg(formatMessage);
        return businessException;
    }

    @Override
    public String getMessage() {
        return responseDto.getMsg();
    }

    public R getResponseDto() {
        return responseDto;
    }
}
