package com.aeye.aeaimb.hugegraph.constant;

import java.util.Arrays;
import java.util.List;

public interface CommonConstants {
	/**
	 * 取值定量属性
	 */
	List<String> quantitative = Arrays.asList("体温", "脉率", "呼吸频率", "收缩压", "舒张压");

	/**
	 * 分组名称
	 */
	List<String> groupName = Arrays.asList("分组1", "分组2", "分组3", "分组4", "分组5", "分组6","分组7", "分组8", "分组9", "分组10", "分组11", "分组12");

	/**
	 * 权重
	 */
	List<String> weight = Arrays.asList("金标准", "重要依据", "一般依据", "轻微依据", "无关", "否定依据");


	List<String> symptomElement = Arrays.asList("发病规律", "发病特征", "诱因", "部位", "性质", "程度", "加重或缓解因素");


	List<String> dingXing = Arrays.asList("不规则抗体", "Rh(D)血型", "ABO血型", "交叉配血", "血清抗艾滋病抗体", "结论");


}
