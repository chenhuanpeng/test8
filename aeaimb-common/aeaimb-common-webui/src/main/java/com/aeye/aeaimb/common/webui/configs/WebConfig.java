package com.aeye.aeaimb.common.webui.configs;


import com.aeye.aeaimb.common.webui.logmark.LogInfoHandler;
import com.aeye.aeaimb.common.webui.logmark.Slf4jLogInfoHandler;
import com.aeye.aeaimb.common.webui.logmark.SysLogMarkAspect;
import com.aeye.aeaimb.common.webui.helper.RequestInfoHelper;
import com.aeye.aeaimb.common.webui.helper.StreamHelper;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;

@Configuration
@Import(TraceIdFilter.class)
public class WebConfig {

	/**
	 * 日期转换器
	 * @return
	 */
	@Bean
	public Converter<String, Date> converter() {
		return StringDateConverter.getInstance();
	}


	/**
	 * 返回值处理
	 * @return
	 */
	@Bean
	public ExceptionResponseBodyAdvice exceptionResponseBodyAdvice(){
		return new ExceptionResponseBodyAdvice();
	}
	@Bean
	public NormalResponseBodyAdvice normalResponseBodyAdvice(){
		return new NormalResponseBodyAdvice();
	}
	@Bean
	public SupportOldProjectAdvice supportOldProjectAdvice(){
		return new SupportOldProjectAdvice();
	}

	/**
	 * 参数处理器
	 * request.getInputStream 的参数处理，一般针对于 json 流参数
	 * @return
	 */
	@ConditionalOnMissingBean(StreamParamHandlerAdvice.class)
	@Bean
	public StreamParamHandlerAdvice streamParamHandlerAdvice(){
		return new StreamParamHandlerAdvice();
	}

	/**
	 * 参数处理器
	 * request.getParam 参数处理，一般针对于 form-data 参数和 x-www-form-urlencoded 参数处理
	 * @return
	 */
	@ConditionalOnMissingBean(RequestParamHandlerFilter.class)
	@Bean
	public RequestParamHandlerFilter requestParamHandlerFilter(){
		return new RequestParamHandlerFilter();
	}

	@Bean
	public StreamHelper streamHelper(){
		return new StreamHelper();
	}

	@Bean
	public RequestInfoHelper requestInfoHelper(){
		return new RequestInfoHelper();
	}

	@Bean
	public SysLogMarkAspect sysLogMarkAspect(){
		return new SysLogMarkAspect();
	}

	@Bean
	@ConditionalOnMissingBean(LogInfoHandler.class)
	public LogInfoHandler logInfoHandler(){
		return new Slf4jLogInfoHandler();
	}

	/**
	 * 使用快速失败模式验证参数，只需要第一个错误即可
	 * @return
	 */
	@Bean
	public Validator validator(){
		ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
				.configure()
				.addProperty( "hibernate.validator.fail_fast", "true" )
				.buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();

		return validator;
	}
}
