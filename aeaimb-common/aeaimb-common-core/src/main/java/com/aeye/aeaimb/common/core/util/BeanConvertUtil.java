package com.aeye.aeaimb.common.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.cglib.core.Converter;
import org.springframework.objenesis.ObjenesisStd;

public class BeanConvertUtil {
	private static final Logger log = LoggerFactory.getLogger(BeanConvertUtil.class);
	private static ThreadLocal<ObjenesisStd> OBJENESIS_STD_THREAD_LOCAL = ThreadLocal.withInitial(ObjenesisStd::new);
	private static ConcurrentHashMap<Class<?>, ConcurrentHashMap<Class<?>, BeanCopier>> BEAN_COPIER_CACHE = new ConcurrentHashMap();

	public BeanConvertUtil() {
	}

	public static <T> T convertBean(Object bean, Class<T> targetClass) {
		if (Objects.isNull(bean)) {
			return null;
		} else {
			try {
				T target = targetClass.newInstance();
				BeanUtils.copyProperties(bean, target);
				return target;
			} catch (Exception var4) {
				log.error(var4.getMessage(), var4);
				throw BusinessException.create(SystemMessage.SYS_INNER_PARAM_EX);
			}
		}
	}

	public static <T> List<T> convertListBean(List<?> beanList, Class<T> targetClass) {
		return (List)(CollUtil.isEmpty(beanList) ? Lists.newArrayList() : (List)beanList.stream().map((bean) -> {
			try {
				T target = targetClass.newInstance();
				BeanUtils.copyProperties(bean, target);
				return target;
			} catch (Exception var4) {
				log.error(var4.getMessage(), var4);
				throw BusinessException.create(SystemMessage.SYS_INNER_DATA_CONVERT_ERROR);
			}
		}).collect(Collectors.toList()));
	}

	public static <T> T deepConvertBean(Object source, Class<T> target) {
		return deepConvertBean(source, ((ObjenesisStd)OBJENESIS_STD_THREAD_LOCAL.get()).newInstance(target));
	}

	private static <T> T deepConvertBean(Object source, T target) {
		BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target.getClass());
		beanCopier.copy(source, target, (Converter)null);
		return target;
	}

	public static <T> List<T> deepConvertList(List<?> sources, Class<T> target) {
		if (CollUtil.isEmpty(sources)) {
			return Collections.emptyList();
		} else {
			ArrayList<T> list = new ArrayList(sources.size());
			ObjenesisStd objenesisStd = (ObjenesisStd)OBJENESIS_STD_THREAD_LOCAL.get();
			Iterator var4 = sources.iterator();

			while(var4.hasNext()) {
				Object source = var4.next();
				if (source != null) {
					T newInstance = objenesisStd.newInstance(target);
					BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target);
					beanCopier.copy(source, newInstance, (Converter)null);
					list.add(newInstance);
				}
			}

			return list;
		}
	}

	public static <T> T mapToBean(Map<?, ?> source, Class<T> target) {
		T bean = ((ObjenesisStd)OBJENESIS_STD_THREAD_LOCAL.get()).newInstance(target);
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(source);
		return bean;
	}

	public static <T> Map<?, ?> beanToMap(T source) {
		return BeanMap.create(source);
	}

	private static <S, T> BeanCopier getCacheBeanCopier(Class<S> source, Class<T> target) {
		ConcurrentHashMap<Class<?>, BeanCopier> copierConcurrentHashMap = (ConcurrentHashMap)BEAN_COPIER_CACHE.computeIfAbsent(source, (aClass) -> {
			return new ConcurrentHashMap(16);
		});
		return (BeanCopier)copierConcurrentHashMap.computeIfAbsent(target, (aClass) -> {
			return BeanCopier.create(source, target, false);
		});
	}
}
