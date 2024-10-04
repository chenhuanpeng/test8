package com.aeye.aeaimb.common.webui.configs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * 参数处理器,这个只能对 request.getInputStream 中的参数进行处理
 */
@ControllerAdvice
@Slf4j
public class StreamParamHandlerAdvice implements RequestBodyAdvice {
    private ParamHandler paramHandler;
    public static ThreadLocal<String> STREAM_PARAMS_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return new CustomHttpInputMessage(httpInputMessage);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class CustomHttpInputMessage implements HttpInputMessage {
        private HttpInputMessage origin;

        public CustomHttpInputMessage(HttpInputMessage httpInputMessage) {
            this.origin = httpInputMessage;
        }

        @Override
        public InputStream getBody() throws IOException {
            HttpHeaders headers = origin.getHeaders();
            InputStream body = origin.getBody();

            // 空参，get 请求，流为空，非 application/json 请求，不处理参数
            MediaType contentType = headers.getContentType();
            if(contentType == null){return body;}
            if(!contentType.isCompatibleWith(MediaType.APPLICATION_JSON)){return body;}
            if(body == null){return null;}
            String params = IOUtils.toString(body, "utf-8");
            // 记住流式参数，后面的日志解析需要用到参数，因为后面已经解析不出参数了，或者是处理过后的参数
            STREAM_PARAMS_THREAD_LOCAL.set(params);
            if(StringUtils.isBlank(params)){return body;}

            // 正式过滤 json 参数
            Object parse = JSON.parse(params);
            if (parse instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) parse;
                handlerJsonArray(jsonArray);
            } else if (parse instanceof JSONObject) {
                handlerJsonObject((JSONObject) parse);
            } else {
                log.error("参数不支持去空格:" + parse+ " contentType:"+contentType);
            }

            return IOUtils.toInputStream(JSON.toJSONString(parse, SerializerFeature.WriteMapNullValue), "UTF-8");
        }

        private void handlerJsonObject(JSONObject jsonObject) {
            Iterator<Map.Entry<String, Object>> iterator = jsonObject.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                String key = next.getKey();
                Object value = next.getValue();
                if (value instanceof JSONArray) {
                    handlerJsonArray((JSONArray) value);
                }else if(value instanceof JSONObject){
                    handlerJsonObject((JSONObject) value);
                }else if(value instanceof  String){
                    String needHandlerValue = Objects.toString(value);
                    if(paramHandler != null) {
                        String handler = paramHandler.handler(StringUtils.trim(needHandlerValue));
                        next.setValue(handler);
                    }else{
                        next.setValue(needHandlerValue);
                    }
                }
            }
        }

        private void handlerJsonArray(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                Object object = jsonArray.get(i);
                if(object instanceof JSONObject){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    handlerJsonObject(jsonObject);
                }else if(object instanceof  String){
                    String trimValue = StringUtils.trim(Objects.toString(object, ""));
                    jsonArray.set(i,trimValue);
                }else if(object instanceof JSONArray){
                    log.info("array 中有 array 不支持,需选用别的数据格式");
                }

            }
        }

        @Override
        public HttpHeaders getHeaders() {
            return origin.getHeaders();
        }
    }

    @Autowired(required = false)
    public void setParamHandler(ParamHandler paramHandler) {
        this.paramHandler = paramHandler;
    }
}
