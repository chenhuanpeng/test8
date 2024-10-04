package com.aeye.aeaimb.mkpb.dto.cdn;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 推理疾病定义(WmkgReasonDefine)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Data
@Valid
public class ReasonDefineDTO implements Serializable {
    private static final long serialVersionUID = 925491175499362698L;
    /**
     * 推理ID
     */
	@NotBlank(message = "推理ID不能为空")
    private String reasonId;
    /**
     * 方案名称
     */
    private String reasonName;
    /**
     * 启用状态:1开0关
     */
    private Integer reasonStatus;
    /**
     * 删除标志 0 正常
     */
    private String delFlag;


}

