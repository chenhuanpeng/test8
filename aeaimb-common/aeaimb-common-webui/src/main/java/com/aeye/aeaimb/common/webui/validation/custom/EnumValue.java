package com.aeye.aeaimb.common.webui.validation.custom;//package com.sanri.web.validation.custom;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = {EnumValueValidator.class})
//public @interface EnumValue {
//
//    String message() default "{sanri.webui.validator.constraints.enum.int}";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//    Class<? extends Enum<?>> enumClass();
//
//    /**
//     * 将值解析成枚举类型的方法
//     * @return
//     */
//    String parserEnumMethod();
//}
