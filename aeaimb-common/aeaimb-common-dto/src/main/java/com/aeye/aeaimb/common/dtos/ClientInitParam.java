package com.aeye.aeaimb.common.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ClientInitParam {
	/**
	 * 机器标识
	 * @mock 192.168.57.181
	 */
	private String machine;
	/**
	 * 授权键
	 * @mock xxxxxxxxxx
	 */
	private String autherKey;
	/**
	 * 患者唯一标识
	 * @mock 000001
	 */
	private String userGuid;
	/**
	 * 医生唯一标识
	 * @mock d00000001
	 */
	private String doctorGuid;
	/**
	 * 医院唯一标识
	 * @mock h001
	 */
	private String hospitalGuid;

	/**
	 * 科室编码(HIS传入)
	 */
	private String deptGuid;

	/**
	 * 医生名称
	 * @mock 张三
	 */
	private String doctorName;
	/**
	 * 科室名称
	 * @mock 内科
	 */
	private String department;

	/**
	 * 医院名称
	 * @mock 湘雅附二
	 */
	private String hospitalName;

	/**
	 * 序列号
	 * @mock p00001
	 */
	private String serialNumber;

	/**
	 * 应用场景：1住院; 2门诊; 3急诊
	 */
	private String customEnv;
	/**
	 * 接入类型c门诊版m住院版
	 */
	private String flag;
	/**
	 * 对接方式：1 浮窗;3 内嵌
	 */
	private String accessType;

	/**
	 * 病人名称
	 */
	private String userName;

	/**
	 * 手机号
	 */
	private String phoneNo;

	/**
	 * 性别 1: 男, 2: 女
	 */
	private Integer gender;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 年龄单位: 岁, 月, 天; YEAR, MONTH, DAY
	 */
	private String ageType;

	/**
	 * 就诊时间
	 */
	private Date regTime;
	/**
	 * 就诊类型（字典：1、门诊；2、急诊）
	 */
	private String regType;
	/**
	 * 婚姻状况编码
	 */
	private String marriage;

	/**
	 * 复诊标志: 0 否，1是
	 * 初诊：0  复诊：1
	 */
	private String followTag;

	/**
	 * 就诊人身份证
	 */
	private String idcard;
	/**
	 * 挂号单号
	 */
	private String registerNo;
	/**
	 * 出生日期
	 */
	private String birthDate;

	/**
	 * 是否怀孕，1/0
	 */
	private String pregnancy;
}
