package com.aeye.aeaimb.common.webui.validation.custom;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class IdCard18Validator implements ConstraintValidator<IdCard18,String> {
    @Override
    public void initialize(IdCard18 constraintAnnotation) {
        try {
            InputStream resourceAsStream = IdCard18Validator.class.getResourceAsStream("/areaCodes");
            String areaCodes = IOUtils.toString(resourceAsStream);
            if(StringUtils.isNotBlank(areaCodes)){
                IdCard18Model.areaCodes = StringUtils.split(areaCodes,',');
            }
        } catch (IOException e) {
            log.error("读取 areaCode 异常"+e.getMessage(),e);
        }
    }

    @Override
    public boolean isValid(String idcard, ConstraintValidatorContext constraintValidatorContext) {
        try{
            IdCard18Model idCard18Model = new IdCard18Model(idcard);
            return true;
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
            return false;
        }
    }
}
