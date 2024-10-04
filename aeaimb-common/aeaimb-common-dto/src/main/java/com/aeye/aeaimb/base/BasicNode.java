package com.aeye.aeaimb.base;

import lombok.Data;

import java.util.List;

/**
 * 树节点
 * @author yangjing
 * @date 2024/9/20
 */
@Data
public class BasicNode extends BasicObject{
	/**
	 * 叶子节点
	 */
	private List<BasicObject> nodes;
}
