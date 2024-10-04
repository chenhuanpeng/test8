package com.aeye.aeaimb.mkpb.vo.cstn;

import lombok.Data;

import java.util.List;

@Data
public class TreeObj {
	public String id;
	public String parentId;
	public String weight;
	public String name;
	public String skipCondition;
	public String purposeDesc;
	public String isLoop;
	public String selCondition;
	List<TreeChildNode> children;
}