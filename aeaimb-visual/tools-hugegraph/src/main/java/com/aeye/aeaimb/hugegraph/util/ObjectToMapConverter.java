package com.aeye.aeaimb.hugegraph.util;

import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjectToMapConverter {
	public static Map<String, Object> convertToMap(Object obj) {
		Map<String, Object> result = new HashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true); // 设置为可访问，以便访问私有字段
			String key;

			// 查找@TableField注解
			TableField tableField = field.getAnnotation(TableField.class);
			if (tableField != null && !tableField.value().isEmpty()) {
				key = tableField.value(); // 使用注解中的值作为key
			} else {
				key = field.getName(); // 如果没有@TableField注解，使用字段名作为key
			}

			try {
				Object value = field.get(obj);
				// 检查值是否为空（这里以null和空字符串为例）
				if (value != null && !(value instanceof String && ((String) value).isEmpty())) {
					result.put(key, value); // 将字段名和值放入Map中
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(Map<String, Object> map, Class<T> clazz) {
		try {
			T obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String key;
				TableField tableField = field.getAnnotation(TableField.class);
				if (tableField != null && !tableField.value().isEmpty()) {
					key = tableField.value(); // 使用注解中的值作为key
				} else {
					key = field.getName(); // 如果没有@TableField注解，使用字段名作为key
				}
				if (map.containsKey(key)) {
					field.set(obj, map.get(key));
				}
			}
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
