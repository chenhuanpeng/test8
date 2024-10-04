package com.aeye.aeaimb.common.core.exception;


import com.aeye.aeaimb.common.core.util.R;

import java.text.MessageFormat;

public interface ExceptionCause<T extends Exception> {
    T exception(Object... args);

    R result();

    MessageFormat getMessageFormat();
}
