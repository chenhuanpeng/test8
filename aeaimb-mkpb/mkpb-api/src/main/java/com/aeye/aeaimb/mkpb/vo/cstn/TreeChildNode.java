package com.aeye.aeaimb.mkpb.vo.cstn;


import lombok.Data;

@Data
public class TreeChildNode {
	public String id;
	public String parentId;
	public String weight;
	public String name;
	public String skipCondition;
	public String purposeDesc;
	public String isLoop;
	public String selCondition;
}