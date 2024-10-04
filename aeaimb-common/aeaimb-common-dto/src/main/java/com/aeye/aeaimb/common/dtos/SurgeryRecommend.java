package com.aeye.aeaimb.common.dtos;

import lombok.Data;

/**
 * 手术推荐
 */
@Data
public class SurgeryRecommend {
	/**
	 * 手术编码
	 * @mock 1111
	 */
	private String code;
	/**
	 * 手术名称
	 * @mock 腹腔镜胆囊切除术
	 */
	private String name;
	/**
	 * 注意事项
	 */
	private String notes;
	/**
	 * 推荐归因
	 * @mock 胆囊切除术是胆囊结石的首选治疗方法，腹腔镜胆囊切除术具有创伤小、恢复快、美容效果好、住院时间短等特点。
	 */
	private String desc;
	/**
	 * 麻醉方式编码
	 * @mock 234324
	 */
	private String anaesthesiaCode;

	/**
	 * 麻醉方式名称
	 * @mock 全身麻醉
	 */
	private String anaesthesiaName;

	/**
	 * 切口类型编码
	 * @mock 32142
	 */
	private String incisionTypeCode;

	/**
	 * 切口类型名称
	 * @mock Ⅱ类切口（清洁-污染）
	 */
	private String incisionTypeName;

	/**
	 * 其它信息
	 */
	private String remark;


	/**
	 * 用于响应给前端使用
	 */
	protected String kgType;
	/**
	 * 标记 his 已经填了
	 */
	protected boolean hisMark;
}
