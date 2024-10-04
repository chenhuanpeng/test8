package com.aeye.aeaimb.mkpb.dto.cdn.symptom;

import lombok.Data;

import java.io.Serializable;

/**
 * 推理规则条件症状要素(WmkgReasonCdnSympElmt)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:22
 */
@Data
public class AddReasonCdnSympElmtDTO implements Serializable {
    private static final long serialVersionUID = 742020628500544850L;
    /**
     * 条件ID
     */
    private String cdnId;
    /**
     * 症状编码
     */
    private String symptomCode;
    /**
     * 症状名称
     */
    private String symptomName;

	private String symptomMain;



    /**
     * 因子编码
     */
    private String factorType;
    /**
     * 因子名称
     */
    private String factorName;
    /**
     * 因子值
     */
    private String factorValue;


	private Integer factorSort;



	/**
	 * 条件权重
	 */
	private String factorWeight;
	/**
	 * 否定条件:是/否
	 */
	private String factorNeg;
	/**
	 * 必选条件:是/否
	 */
	private String factorMust;
	/**
	 * 值性质:定性、定量
	 */
	private String factorDefine;
	/**
	 * 因子数据类型：多个值、单个值
	 */
	private String factorDataType;
	/**
	 * 因子数据单位
	 */
	private String factorUnit;
	/**
	 * 规则组ID
	 */
	private String groupId;
	/**
	 * 推理ID
	 */
	private String reasonId;

}

