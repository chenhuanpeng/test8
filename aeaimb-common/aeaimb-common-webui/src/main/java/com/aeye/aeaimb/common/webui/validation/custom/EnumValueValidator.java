package com.aeye.aeaimb.common.webui.validation.custom;//package com.sanri.web.validation.custom;
//
//import org.apache.commons.lang3.EnumUtils;
//import org.apache.commons.lang3.reflect.MethodUtils;
//import org.apache.logging.log4j.util.Strings;
//import org.springframework.cglib.core.ReflectUtils;
//import org.springframework.util.ReflectionUtils;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//
//public class EnumValueValidator implements ConstraintValidator<EnumValue,Object> {
//    private Class<? extends Enum<?>> enumClass;
//    private String parserEnumMethod;
//
//    @Override
//    public void initialize(EnumValue constraintAnnotation) {
//        parserEnumMethod = constraintAnnotation.parserEnumMethod();
//        enumClass = constraintAnnotation.enumClass();
//    }
//
//    @Override
//    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        if (value == null) {
//            return Boolean.TRUE;
//        }
//
//        if (enumClass == null || parserEnumMethod == null) {
//            return Boolean.TRUE;
//        }
//
//        try {
//            //判断方法是否存在
//            Method method = ReflectionUtils.findMethod(enumClass, parserEnumMethod, value.getClass());
//            if(method == null){
//                throw new IllegalArgumentException("根据请求类型["+value.getClass()+"]在枚举类["+enumClass+"] 找不到解析枚举值的方法 ["+parserEnumMethod+"]");
//            }
//            //检测方法返回值是不是最终枚举类型，是否为静态方法
//            if (enumClass != method.getReturnType() && !Modifier.isStatic(method.getModifiers())) {
//                throw new IllegalArgumentException("["+method+"] 不是静态方法或返回值不是枚举类型["+enumClass+"]");
//            }
//
//            Object enumValue  = MethodUtils.invokeStaticMethod(enumClass,parserEnumMethod,value);
//            if(enumValue == null)return Boolean.FALSE;
//
//            return true;
//        } catch (NoSuchMethodException |IllegalAccessException e ) {
//            ReflectionUtils.handleReflectionException(e);
//        } catch ( InvocationTargetException  e){
//            ReflectionUtils.handleInvocationTargetException(e);
//        }
//        return false;
//    }
//}
