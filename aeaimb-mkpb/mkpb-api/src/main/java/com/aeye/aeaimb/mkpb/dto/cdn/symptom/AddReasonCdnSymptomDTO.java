package com.aeye.aeaimb.mkpb.dto.cdn.symptom;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 推理规则条件症状(WmkgReasonCdnSymptom)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:22
 */
@Data
public class AddReasonCdnSymptomDTO implements Serializable {
    private static final long serialVersionUID = 543629438420274627L;
    /**
     * 症状编码
     */
    private String symptomCode;
    /**
     * 症状名称（用于显示，不关联）
     */
    private String symptomName;
    /**
     * 主要症状：是/否
     */
    private String symptomMain;
	/**
	 * 症状权重
	 */
    private String factorWeight;

	/**
	 * 症状排序
	 */
	private Integer factorSort;

	/**
	 * 症状元素
	 */
	List<AddReasonCdnSympElmtDTO> symptomElmtList;


}

