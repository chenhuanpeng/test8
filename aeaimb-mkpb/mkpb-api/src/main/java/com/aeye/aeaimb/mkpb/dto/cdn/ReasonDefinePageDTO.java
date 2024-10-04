package com.aeye.aeaimb.mkpb.dto.cdn;

import com.aeye.aeaimb.common.dtos.BaseQryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 医疗机构密钥查询
 * </p>
 *
 * @author linkk
 * @since 2023-11-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReasonDefinePageDTO extends BaseQryDto implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 * 疾病名称
	 */
	private String diseaseName;
	/**
	 * 疾病编码
	 */
	private String diseaseCode;

	/**
	 * 方案名称
	 */
	private String reasonName;
	/**
	 * 方案上线下线状态，0：下线，1：上线
	 */
	private String reasonStatus;

}
