package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 分页查询基础DTO
 **/
@Data
public class BaseQryDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * 当前页,比如0
	 */
	@NotNull(message = "当前页不能为空")
	protected Integer current =1;

	/**
	 * 每页记录数,比如10
	 */
	@NotNull(message = "每页记录数不能为空")
	protected Integer size =10;


	/**
	 * 排序列,多个用英文逗号隔开
	 */
	private String orderByColumn;

	/**
	 * 排序的方向desc或者asc
	 */
	private String isAsc;

}
