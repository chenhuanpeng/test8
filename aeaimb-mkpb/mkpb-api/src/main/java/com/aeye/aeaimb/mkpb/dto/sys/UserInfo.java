
package com.aeye.aeaimb.mkpb.dto.sys;

import com.aeye.aeaimb.mkpb.entity.sys.SysUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lengleng
 * @date 2017/11/11
 */
@Data
@Schema(description = "用户信息")
public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	@Schema(description = "用户基本信息")
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限标识集合")
	private String[] permissions;

	/**
	 * 角色集合
	 */
	@Schema(description = "角色标识集合")
	private Long[] roles;

}
