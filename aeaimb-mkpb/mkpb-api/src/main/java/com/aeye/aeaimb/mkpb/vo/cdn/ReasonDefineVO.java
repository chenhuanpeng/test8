package com.aeye.aeaimb.mkpb.vo.cdn;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 推理疾病定义(WmkgReasonDefine)实体类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Data
public class ReasonDefineVO implements Serializable {
    private static final long serialVersionUID = 925491175499362698L;
    /**
     * 推理ID
     */
    private String reasonId;
    /**
     * 方案名称
     */
    private String reasonName;
    /**
     * 疾病名称
     */
    private String diseaseName;
    /**
     * 疾病编码
     */
    private String diseaseCode;
    /**
     * 推理排序
     */
    private Integer diseaseSort;
    /**
     * 启用状态:1开0关
     */
    private Integer reasonStatus;
    /**
     * 金标依据比例
     */
    private String imptRatio;
    /**
     * 重要依据比例
     */
    private String genlRatio;
    /**
     * 一般依据比例
     */
    private String minoRatio;
    /**
     * 无关依据比例
     */
    private String negvRatio;
    /**
     * 负向依据比例
     */
    private String irreRatio;
    /**
     * 全院/专科
     */
    private String scopeType;
    /**
     * 专科名称
     */
    private String scopeValue;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标志 0 正常
     */
    private String delFlag;


}

