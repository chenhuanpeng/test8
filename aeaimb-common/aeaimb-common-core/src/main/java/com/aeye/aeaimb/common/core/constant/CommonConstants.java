package com.aeye.aeaimb.common.core.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1
 */
public interface CommonConstants {

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";

	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单树根节点
	 */
	Long MENU_TREE_ROOT_ID = -1L;

	String MENU_TREE_ROOT_STR = "";

	String ZERO = "0";

	String TWO = "2";

	/**
	 * 菜单树根节点(应用)
	 */
	Long MENU_TREE_APP_ROOT_ID = -99L;

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json; charset=utf-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "cdss-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "cdss";

	/**
	 * 成功标记
	 */
	String SUCCESS = "0";

	/**
	 * 失败标记
	 */
	String FAIL = "1";

	/**
	 * 当前页
	 */
	String CURRENT = "current";

	/**
	 * size
	 */
	String SIZE = "size";

	/**
	 * 请求开始时间
	 */
	String REQUEST_START_TIME = "REQUEST-START-TIME";

	/**
	 * 超管账号配置
	 */
	List<String> superUserName = Arrays.asList("admin", "admin2");
	/**
	 * 超管角色名称
	 */
	List<String> superRoleName = Arrays.asList("ROLE_ADMIN", "CODE_GEN");
	/**
	 * 超管角色ID
	 */
	Long superRoleId = 1L;


}
