package com.aeye.aeaimb.common.webui.configs;

import com.aeye.aeaimb.common.core.util.R;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeResponse {
	/**
	 * 转换成 dto 类型
	 * @return
	 */
	@AliasFor("value")
	Class<? extends R> type() ;

	/**
	 * 根结点编号
	 * @return
	 */
	String rootId()  default "";

	/**
	 * 根结点的 parentId 编号，默认 -1
	 * 此设置用于生成森林
	 * 当 rootId 和 rootParentId 同时设置时，以性能优先，先使用 rootId 生成单根树
	 * @return
	 */
	String rootParentId() default "-1";
}
