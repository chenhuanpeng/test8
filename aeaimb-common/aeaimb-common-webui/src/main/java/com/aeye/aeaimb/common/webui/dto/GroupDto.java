package com.aeye.aeaimb.common.webui.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto<G,I> {
	private G group;
	private List<I> items;

	public GroupDto() {
	}

	public GroupDto(G group, List<I> items) {
		this.group = group;
		this.items = items;
	}
}
