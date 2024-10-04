package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author linkeke
 * 枚举类
 */
public class CommonEnum {

	/**
	 * 密钥生成状态
	 */
	@Getter
	@AllArgsConstructor
	public enum CreateStatus {
		COMMON(1, "已生成"),
		NO_COMMON(0, "未生成");
		private final Integer type;
		private final String description;
	}

	/**
	 * 应用状态：1开启，0关闭
	 */
	@Getter
	@AllArgsConstructor
	public enum ApplicationStatus {
		COMMON("1", "开启"),
		NO_COMMON("0", "关闭");
		private final String type;
		private final String description;
	}

	/**
	 * 是否有权限，1有，0没有
	 */
	@Getter
	@AllArgsConstructor
	public enum HasAuth {
		YES("1", "有"),
		NO("0", "没有");
		private final String type;
		private final String description;
	}

	/**
	 * 是否有效，1是，0否
	 */
	@Getter
	@AllArgsConstructor
	public enum YesOrNo {
		YES(1, "是"),
		NO(0, "否");
		private final Integer type;
		private final String description;
	}

	@Getter
	@AllArgsConstructor
	public enum YesOrNoStr {
		YES("1", "是"),
		NO("0", "否");
		private final String type;
		private final String description;
	}

	/**
	 * 删除标志 1删除 0正常
	 */
	@Getter
	@AllArgsConstructor
	public enum DELETE_FLAG {
		YES("1", "是"),
		NO("0", "否");
		private final String type;
		private final String description;
	}


	@Getter
	@AllArgsConstructor
	public enum APP_MODULE {
		MODULE_1("1", "智能推荐","/home/proposal"),
		MODULE_2("2", "诊疗实况","/home/playBack"),
		MODULE_3("3", "质控提醒","/home/qualityRemind"),
		MODULE_4("4", "知识检索","/home/knowledge"),
		MODULE_5("6", "智能回答","/home/question"),
		MODULE_6("5", "患者360","/home/patient360"),
		MODULE_7("7", "统计分析","/home/analyse");
		private final String type;
		private final String description;
		private final String path;

		public static APP_MODULE findByType(String type) {
			for (APP_MODULE moduleValue : values()) {
				if (moduleValue.getType().equals(type)) {
					return moduleValue;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum OrgType {
		OrgType_1("1", "管理机构"),
		OrgType_2("2", "医院"),
		OrgType_3("3", "社区卫生服务中心"),
		OrgType_4("4", "社区卫生服务站"),
		OrgType_5("5", "卫生院"),
		OrgType_6("6", "门诊部"),
		OrgType_7("7", "医务室"),
		OrgType_8("8", "妇幼保健院");
		private final String type;
		private final String description;


		public static OrgType findByType(String type) {
			for (OrgType orgTypeValue : values()) {
				if (orgTypeValue.getType().equals(type)) {
					return orgTypeValue;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum ServiceType {
		ServiceType_1("1", "检查"),
		ServiceType_2("2", "检验"),
		ServiceType_3("3", "操作治疗"),
		ServiceType_4("4", "量表");
		private final String type;
		private final String description;


		public static ServiceType findByType(String type) {
			for (ServiceType serviceType : values()) {
				if (serviceType.getType().equals(type)) {
					return serviceType;
				}
			}
			return null;
		}
	}

	/**
	 *
	 */
	@Getter
	@AllArgsConstructor
	public enum SysDictEnum {
		SYS_DICT_ITEM("SYS_DICT_ITEM", "字典值域"),
		SYS_DICT("SYS_DICT", "字典类型");
		private final String value;
		private final String message;

		public static SysDictEnum getByValue(String value) {
			for (SysDictEnum state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	/**
	 * 患者来源
	 */
	@Getter
	@AllArgsConstructor
	public enum FromType {
		add("add", "手工"),
		his("his", "同步");
		private final String value;
		private final String message;

		public static FromType getByValue(String value) {
			for (FromType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * 受邀记录状态
	 */
	@Getter
	@AllArgsConstructor
	public enum ApplyStatus {
		pending("pending", "待接受"),
		progress("progress", "进行中"),
		refused("refused", "拒诊"),
		ended("ended", "已结束"),
		expired("expired", "超时未接诊");
		private final String value;
		private final String message;

		public static ApplyStatus getByValue(String value) {
			for (ApplyStatus state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * 聊天室的消息类型
	 */
	@Getter
	@AllArgsConstructor
	public enum MessageType {
		file("file", "文件"),
		image("image", "图片"),
		text("text", "文本"),
		event("event", "事件"),
		voice("voice", "语音"),
		custom("custom", "自定义");
		private final String value;
		private final String message;

		public static MessageType getByValue(String value) {
			for (MessageType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * type暂时有协助申请单，报告单两种
	 */
	@Getter
	@AllArgsConstructor
	public enum MessageContentType {
		apply("apply", "协助申请单"),
		report("report", "报告单"),
		grouplist("grouplist", "参名名单");
		private final String value;
		private final String message;

		public static MessageContentType getByValue(String value) {
			for (MessageContentType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * sence	场景 	rmdt, cdss, config
	 */
	@Getter
	@AllArgsConstructor
	public enum SenceType{
		rmdt("rmdt", "远程协助"),
		cdss("cdss", "智能推荐"),
		config("config", "配置");
		private final String value;
		private final String message;

		public static SenceType getByValue(String value) {
			for (SenceType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * 角色中区分
	 * 来源标志，sys代表系统，org代表机构
	 */
	@Getter
	@AllArgsConstructor
	public enum SourceFromType{
		sys("sys", "系统运维"),
		org("org", "机构管理");
		private final String value;
		private final String message;

		public static SourceFromType getByValue(String value) {
			for (SourceFromType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum AgeType{
		YEAR("YEAR", "岁"),
		MONTH("MONTH", "月"),
		DAY("DAY", "天");
		private final String value;
		private final String message;

		public static AgeType getByValue(String value) {
			for (AgeType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum ClientModeType{
		DEPT("dept", "按科室"),
		ORG("org", "按医院");
		private final String value;
		private final String message;

		public static ClientModeType getByValue(String value) {
			for (ClientModeType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum ClientModeValue{
		western("western", "西医版"),
		east("east", "中医版");
		private final String value;
		private final String message;

		public static ClientModeValue getByValue(String value) {
			for (ClientModeValue state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum DiseaseLabelValue{
		wei("危重病", "危",1),
		cj("传染病(甲类)", "传(甲)",2),
		cy("传染病(乙类)", "传(乙)",2),
		cb("传染病(丙类)", "传(丙)",2),
		zyb("职业病", "职",3),
		hjb("罕见病", "罕",4),
		dfb("地方病", "地",5);
		private final String value;
		private final String message;
		private final Integer sort;

		public static DiseaseLabelValue getByValue(String value) {
			for (DiseaseLabelValue state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}
}
