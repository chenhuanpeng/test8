package com.aeye.aeaimb.common.dtos;

import lombok.Data;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;

@Data
public class Rationality {
    /**
     * 级别 warning ban
     * @mock 3
     */
	private String level;
    /**
     * 标签
     * @mock 适应症
     */
	private String label;
    /**
     * 标签说明
     * @mock 。患者症状为腹痛、腹泻，碳酸氢钠片可能不适用。
     */
	private String desc;

	/**
	 * 相干项目项
	 */
	private List<RelativeItem> relativeItems;

	public Rationality(String level, String label, String desc) {
		this.level = level;
		this.label = label;
		this.desc = desc;
	}

	public Rationality() {
	}

	public String getLabel() {
		if (!StringUtils.hasText(label)){
			return label;
		}
		return StringUtils.replace(label, "。","");
	}

	/**
	 * 药品合理性等级
	 */
	@Getter
	public enum SeverityLevel{
		RL001("RL001","1","禁用"),
		RL002("RL002","2","不推荐"),
		RL003("RL003","3","慎用"),
		RL004("RL004","3","提示");
		private String code;
		private String level;
		private String desc;

		SeverityLevel(String code,String level, String desc) {
			this.code = code;
			this.level = level;
			this.desc = desc;
		}

		/**
		 * 获取code对应的level
		 * @param code
		 * @return
		 */
		public static String getLevelByCode(String code) {
			for(SeverityLevel severity: SeverityLevel.values()){
				if(severity.getCode().equals(code)){
					return severity.getLevel();
				}
			}
			return null;
		}
	}
}
