package com.aeye.aeaimb.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TreatmentPlan extends CommMark{
	/**
	 * 推荐归因(富文本)
	 */
	private String text;


	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;

	/**
	 * 推荐归因
	 */
	private List<ReasonContent> texts;

	/**
	 * 概述字段
	 */
	public static final String OVERVIEW = "overview";

	/**
	 * 获取所有的标题
	 * @return
	 */
	@JsonIgnore
	public List<String> getTitles(){
		if (CollectionUtils.isEmpty(texts)){
			return new ArrayList<>();
		}
		List<String> collect = texts.stream().filter(item -> !OVERVIEW.equals(item.getTextTitle()))
				.map(ReasonContent::getTextTitle).collect(Collectors.toList());
		return collect;
	}

	/**
	 * 概述标识
	 * @return
	 */
	public String overviewMark(){
		return OVERVIEW;
	}

	/**
	 * 获取概述
	 * @return
	 */
	@JsonIgnore
	public String getOverview(){
		if (CollectionUtils.isEmpty(texts)){
			return null;
		}
		ReasonContent treatmentPlanContent = texts.get(0);
		if (OVERVIEW.equals(treatmentPlanContent.getTextTitle())){
			return treatmentPlanContent.getTextContent();
		}
		return null;
	}

}
