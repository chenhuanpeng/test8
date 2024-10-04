package com.aeye.aeaimb.common.dtos;

import lombok.Data;

@Data
public class HisCommParam {
    /**
     * 授权键
     * @mock xxxxxxxxxx
     */
    protected String autherKey;
    /**
     * 患者唯一Id
     * @mock p00000001
     */
    protected String userGuid;
    /**
     * 患者名字
     * @mock 张三
     */
    protected String userName;
    /**
     * 触发时机
     * {@link PageSource}
     * @mock 62
     */
    protected Integer pageSource;
    /**
     * 就诊序列号
     * @mock p00000000001
     */
    protected String serialNumber;
    /**
     * 科室名称
     * @mock 内科
     */
    protected String deptName;
    /**
     * 科室ID
     * @mock 77889900
     */
    protected String deptGuid;

    /**
     * 登录医生编码
     */
    protected String doctorGuid;

    /**
     * 登录医生名称
     */
    protected String doctorName;

    /**
     * 医院唯一标识
     * @mock h001
     */
    protected String hospitalGuid;
    /**
     * 挂号时间
     */
    protected String regDatetime;

    /**
     * 就诊时间
     */
    protected String visitDate;
    /**
     * 初复诊标志
     * 初诊：0  复诊：1
     */
    protected Integer visitFlag;
    /**
     * 主场景
     * c 门诊版 m 住院版
     */
    protected String mainScene;
    /**
     * 应用场景
     * 医生工作站：01
     * 护理工作站：02
     * 病案工作站：03
     * 其它：00
     */
    protected String appScene;
    /**
     * 业务场景
     * 病案首页页面：01
     * 病历页面：02
     * 医嘱页面：03
     * 护理页面：04
     * 检验结果页面：05
     * 检查结果页面：06
     * 费用明细页面：07
     * 诊断页面：08
     * 检查申请单：20
     * 检验申请单：21
     * 输血申请单：22
     * 手术申请单：23
     * 会诊申请单：24
     * 评估表：40
     */
    protected String bizScene;

    /**
     * 中西医差异模式 western 西医模式,  east 中医模式
     * @mock
     */
    protected String tcmModel;
	/**
	 * 身份证号
	 */
	protected String idcard;

	protected String department;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department){
		this.department = department;
		this.deptName = department;
	}
}
