/*
 * Copyright (c) 2020 cdss4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aeye.aeaimb.common.feign.sentinel.handle;

import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.RemoteException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.common.core.util.WebUtils;
import com.alibaba.csp.sentinel.Tracer;
import com.aeye.aeaimb.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 全局异常处理器结合sentinel 全局异常处理器不能作用在 oauth server https://gitee.com/log4j/cdss/issues/I1M2TJ
 * </p>
 *
 * @author lengleng
 * @date 2020-06-29
 */
@Slf4j
@Order(10000)
@RestControllerAdvice
@ConditionalOnExpression("!'${security.oauth2.client.clientId}'.isEmpty()")
public class GlobalBizExceptionHandler {

	/**
	 * 全局异常.
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public R handleGlobalException(Exception e) {
		log.error("全局异常信息 ex={}", e.getMessage(), e);

		// 业务异常交由 sentinel 记录
		Tracer.trace(e);
		return R.failed(e.getLocalizedMessage());
	}

	/**
	 * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
	 * @see Assert#hasLength(String, String)
	 * @see Assert#hasText(String, String)
	 * @see Assert#isTrue(boolean, String)
	 * @see Assert#isNull(Object, String)
	 * @see Assert#notNull(Object, String)
	 * @param exception 参数校验异常
	 * @return API返回结果对象包装后的错误输出结果
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	public R handleIllegalArgumentException(IllegalArgumentException exception) {
		log.error("非法参数,ex = {}", exception.getMessage(), exception);
		return R.failed(exception.getMessage());
	}

	/**
	 * AccessDeniedException
	 * @param e the e
	 * @return R
	 */
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public R handleAccessDeniedException(AccessDeniedException e) {
		String msg = SpringSecurityMessageSource.getAccessor()
			.getMessage("AbstractAccessDecisionManager.accessDenied", e.getMessage());
		log.warn("拒绝授权异常信息 ex={}", msg);
		return R.failed(e.getLocalizedMessage());
	}

//	/**
//	 * validation Exception
//	 * @param exception
//	 * @return R
//	 */
//	@ExceptionHandler({ MethodArgumentNotValidException.class })
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public R handleBodyValidException(MethodArgumentNotValidException exception) {
//		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//		log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
//		return R.failed(String.format("%s %s", fieldErrors.get(0).getField(), fieldErrors.get(0).getDefaultMessage()));
//	}
//
//	/**
//	 * validation Exception (以form-data形式传参)
//	 * @param exception
//	 * @return R
//	 */
//	@ExceptionHandler({ BindException.class })
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public R bindExceptionHandler(BindException exception) {
//		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//		log.warn("参数绑定异常,ex = {}", fieldErrors.get(0).getDefaultMessage());
//		return R.failed(fieldErrors.get(0).getDefaultMessage());
//	}

	@Value("${sanri.webui.package.prefix:com.aeye}")
	protected String packagePrefix;

	/**
	 * 处理业务异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	public R businessException(BusinessException e){
		printLocalStackTrack(e);
		return e.getResponseDto();
	}

	@ExceptionHandler(RemoteException.class)
	public R remoteException(RemoteException e){
		R parentResult = e.getParent().getResponseDto();
		R resultEntity = e.getResponseDto();
		//返回给前端的是业务错误，但是需要在控制台把远程调用异常给打印出来
		log.error(parentResult.getCode()+":"+parentResult.getMsg()
				+" \n -| "+resultEntity.getCode()+":"+resultEntity.getMsg());

		printLocalStackTrack(e);

		//合并两个结果集返回(远端异常存储在 data 中)
		R merge = R.err(parentResult.getCode())
				.msg(parentResult.getMsg())
				.data(resultEntity);
		return merge;
	}

	/**
	 * 打印只涉及到项目类调用的异常堆栈
	 * @param e
	 */
	private void printLocalStackTrack(BusinessException e) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		List<StackTraceElement> localStackTrack = new ArrayList<>();
		StringBuffer showMessage = new StringBuffer();
		if (ArrayUtils.isNotEmpty(stackTrace)) {
			for (StackTraceElement stackTraceElement : stackTrace) {
				String className = stackTraceElement.getClassName();
				int lineNumber = stackTraceElement.getLineNumber();
				if (className.startsWith(packagePrefix)) {
					localStackTrack.add(stackTraceElement);
					showMessage.append("\t" + className + "(" + lineNumber + ")\n");
				}
			}
			log.error("业务异常: " + e.getMessage() + "\n" + showMessage);
		} else {
			log.error("业务异常,没有调用栈 " + e.getMessage());
		}
	}

	@ExceptionHandler(value = BindException.class)
	public R bindException(BindException ex) {
		// ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用ex.getAllErrors()
		FieldError fieldError = ex.getFieldError();
		assert fieldError != null;
		return SystemMessage.ARGS_ERROR.exception(fieldError.getField(),fieldError.getRejectedValue()).getResponseDto();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R methodArgumentNotValidException(MethodArgumentNotValidException e){
		FieldError fieldError = e.getBindingResult().getFieldError();
		assert fieldError != null;
		R responseDto = SystemMessage.ARGS_ERROR.exception(fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage())
				.getResponseDto();
		return responseDto;
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	public R constraintViolationException(ConstraintViolationException ex){
		ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
		String message = constraintViolation.getMessage();
		return SystemMessage.ARGS_ERROR2.exception(message).getResponseDto();
	}

	@ExceptionHandler(IOException.class)
	public R ioException(IOException e){
		log.error(e.getMessage(),e);
		return SystemMessage.NETWORK_ERROR.result();
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public R httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
		final MediaType contentType = e.getContentType();
		final Optional<HttpServletRequest> request = WebUtils.getRequest();
		if (request.isPresent()){
			final HttpServletRequest httpServletRequest = request.get();
			final String requestURI = httpServletRequest.getRequestURI();
			log.error("请求路径为 : {}, 不支持 MediaType: {}", requestURI, contentType);
		}else {
			log.error("不支持的 MediaType: {}", contentType);
		}

		return SystemMessage.NOT_SUPPORT_MIME.result(contentType);
	}
}
