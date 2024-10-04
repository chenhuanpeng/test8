package com.aeye.aeaimb.common.core.config;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CustomDeserializer extends JsonDeserializer<String> {
	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext context)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		// 将当前解析的JSON结构转换为相应的JSON字符串
		if (jsonParser.getCurrentToken().equals(JsonToken.START_ARRAY)) {
			// 处理数组
			return mapper.writeValueAsString(mapper.readTree(jsonParser));
		} else if (jsonParser.getCurrentToken().equals(JsonToken.START_OBJECT)) {
			// 处理对象
			return mapper.writeValueAsString(mapper.readTree(jsonParser));
		}

		// 其他情况，默认作为字符串处理
		return jsonParser.getText();
	}
}
