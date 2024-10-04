package com.aeye.aeaimb.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author linkeke
 * 枚举类
 */
public class TcmsEnum {

	/**
	 * tcms 统一所有的主要对象编码
	 */
	@Getter
	@AllArgsConstructor
	public enum TcmsObjectType{
		diagnosis("1", "疾病"),
		syndrome("2", "证候"),
		symptom("3", "症状"),
		formula("4", "方剂"),
		herb("5", "中草药"),
		ctpm("6", "中成药"),
		oral("7", "内服"),
		motion("8", "功法"),
		etherapy("9", "外治"),
		therapy("10", "治法"),
		acupoint("11", "经络腧穴"),
		constitution("12", "体质"),
		undisease("13", "未病"),
		body("14", "部位"),
		particle("15", "颗粒"),
		dept("16", "科室"),
		sex("17", "性别"),
		pregnancy("18", "妊娠");
		private final String value;
		private final String message;

		public static TcmsObjectType getByValue(String value) {
			for (TcmsObjectType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * 因地：place，因时：timing,因人：beduine
	 */
	@Getter
	@AllArgsConstructor
	public enum TcmsFormulaVaryType{
		timing("timing", "因时"),
		place("place", "因地"),
		beduine("beduine", "因人"),
		plug("plug", "补充");
		private final String value;
		private final String message;

		public static TcmsFormulaVaryType getByValue(String value) {
			for (TcmsFormulaVaryType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum TcmsTabooSexType{
		man("1", "男"),
		women("2", "女");
		private final String value;
		private final String message;

		public static TcmsTabooSexType getByValue(String value) {
			for (TcmsTabooSexType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum TcmsTabooPregnancyType{
		Pregnancy("1", "有孕"),
		NoPregnancy("0", "无孕");
		private final String value;
		private final String message;

		public static TcmsTabooPregnancyType getByValue(String value) {
			for (TcmsTabooPregnancyType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum TcmsHerbAdrType{
		Pregnancy("妊娠慎用", "妊娠慎用"),
		NoPregnancy("妊娠禁用", "妊娠禁用"),
		Poison("有毒", "有毒");
		private final String value;
		private final String message;

		public static TcmsHerbAdrType getByValue(String value) {
			for (TcmsHerbAdrType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum TcmsHerbAntiType{
		Pregnancy("1", "相畏"),
		NoPregnancy("2", "相反");
		private final String value;
		private final String message;

		public static TcmsHerbAntiType getByValue(String value) {
			for (TcmsHerbAntiType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum TcmsDiagramsType{
		Wood("1", "木"),
		Fire("2", "火"),
		Earth("3", "土"),
		Metal("4", "金"),
		Water("5", "水");
		private final String value;
		private final String message;

		public static TcmsDiagramsType getByValue(String value) {
			for (TcmsDiagramsType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}


	@Getter
	@AllArgsConstructor
	public enum VaryPlaceType{
		Wood("1", "南"),
		Fire("2", "北");
		private final String value;
		private final String message;

		public static VaryPlaceType getByValue(String value) {
			for (VaryPlaceType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum VaryTimeType{
		Spring("1", "春"),
		Summer("2", "夏"),
		Autumn("3", "秋"),
		Winter("4", "冬"),
		SummerL("5", "长夏");
		private final String value;
		private final String message;

		public static VaryTimeType getByValue(String value) {
			for (VaryTimeType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum VaryBeduineType{
		Spring("1", "性别"),
		Summer("2", "年龄"),
		Autumn("3", "胖瘦"),
		Winter("4", "体质");
		private final String value;
		private final String message;

		public static VaryBeduineType getByValue(String value) {
			for (VaryBeduineType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	/**
	 * vary_method 方剂加减化裁用量 add 加 增加药材或药量
	 * vary_method 方剂加减化裁用量 sub 减 减少药量
	 * vary_method 方剂加减化裁用量 replace 替换 替换药位
	 * vary_method 方剂加减化裁用量 qu 去 去为删掉药位
	 */
	@Getter
	@AllArgsConstructor
	public enum VaryMethodType{
		Add("add", "加"),
		Sub("sub", "减"),
		Replace("replace", "替换"),
		Qu("qu", "删掉");
		private final String value;
		private final String message;

		public static VaryMethodType getByValue(String value) {
			for (VaryMethodType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum FactorType{
		Tans("1", "痰湿"),
		Feip("2", "肥胖"),
		Yinx("3", "阴虚"),
		Qix("4", "气虚"),
		Xiaos("5", "消瘦"),
		Laor("6", "老人"),
		Nvr("7", "女人"),
		Xuey("8", "血瘀"),
		Qiy("9", "气郁"),
		Pianz("10", "偏重"),
		Chaoz("11", "超重"),
		Yangx("12", "阳虚"),
		Ert("13", "儿童"),
		Dal("14", "大龄"),
		Xuel("15", "学龄"),
		Yout("16", "幼童"),
		Youe("17", "幼儿"),
		Yinge("18", "婴儿");
		private final String value;
		private final String message;

		public static FactorType getByValue(String value) {
			for (FactorType state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}
	}

	@Getter
	@AllArgsConstructor
	public enum HerbOrder {
		JianFu("1", "煎服"),
		BaoJian("2", "包煎"),
		BaoXian("3", "包煎，先煎"),
		ChongFu("4", "冲服"),
		HouXia("5", "后下"),
		XianXia("6", "先煎"),
		Yangfu("7", "烊化（溶服），兑服");
		private final String value;
		private final String message;

		public static HerbOrder getByValue(String value) {
			for (HerbOrder state : values()) {
				if (state.getValue().equals(value)) {
					return state;
				}
			}
			return null;
		}

		public static HerbOrder getByMessage(String message) {
			for (HerbOrder state : values()) {
				if (state.getMessage().equals(message)) {
					return state;
				}
			}
			return null;
		}
	}

}
