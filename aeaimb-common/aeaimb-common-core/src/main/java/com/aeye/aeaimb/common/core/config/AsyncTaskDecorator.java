package com.aeye.aeaimb.common.core.config;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

/**
 * 对于异步任务时, 同样也能获取到 TraceId
 * spring 的异步任务 @Async
 */
public class AsyncTaskDecorator implements TaskDecorator {
	@Override
	public Runnable decorate(Runnable runnable) {
		try {
			RequestAttributes context = RequestContextHolder.currentRequestAttributes();
			Map<String, String> previous = MDC.getCopyOfContextMap();
			return () -> {
				try {
					RequestContextHolder.setRequestAttributes(context);

					if (previous != null) {
						MDC.setContextMap(previous);
					}

					runnable.run();
				} finally {
					RequestContextHolder.resetRequestAttributes();
					MDC.clear();
				}
			};
		} catch (IllegalStateException e) {
			return runnable;
		}
	}
}
