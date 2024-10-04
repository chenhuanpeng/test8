package com.aeye.aeaimb.common.webui.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

@Slf4j
public class StringDateConverter implements Converter<String, Date> {
    private static StringDateConverter INSTANCE = new StringDateConverter();

    private StringDateConverter(){}

    final String[] parsePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S"};

    public Date convert(String source) {
        try {
            return DateUtils.parseDate(source,parsePatterns);
        } catch (ParseException e) {
            log.error("日期解析错误,当前日期为:" + source);
        }
        return  null;
    }

    public static StringDateConverter getInstance() {
        return INSTANCE;
    }
}
