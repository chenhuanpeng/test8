package com.aeye.aeaimb.common.dtos;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Medical extends ProgressNote {
    /**
     * 主诉
     * @mock 头痛12小时，咽痛2天，发热2天
     */
    @FieldNameCN("主诉")
    private String complaints;
    /**
     * 五史信息
     */
    private HistoryInfo historyInfo;
    /**
     * 体格信息
     */
    private Physique physique;

	/**
	 * 实验室检查/辅助检查
	 */
    @FieldNameCN("辅助检查")
	private String labExams;

    /**
     * 诊断信息
     */
    private List<Diagnosis> diagnoses = new ArrayList<>();

    /**
     * 五史信息
     */
    @Data
    public static final class HistoryInfo{
        /**
         * 现病史
         * @mock 患者诉12小时前无明显诱因下出现阵发性头前不适, 以两侧额颞部刺痛为主，早晨为甚，每次发作时间为两到三分钟不等，休息后可缓解，伴咽痛不适，吞咽时感烧灼感，伴发热，无头晕、
         * 无咳嗽、咳痰、无胸闷、胸痛、无腹泻、无寒颤，在家未做特殊处理，为求治疗遂来我院住院治疗，门诊拟“上呼吸道感染”收住我科，患者自起病以来精神、食欲、睡眠差，大小便正常，体力耐力下降。
         */
        @FieldNameCN("现病史")
        private String presentMedicalHistory;
        /**
         * 既往史
         * @mock 无
         */
        @FieldNameCN("既往史")
        private String previousHistory;
        /**
         * 过敏史
         * @mock 无
         */
        @FieldNameCN("过敏史")
        private String allergicHistory;
        /**
         * 个人史
         * @mock 无
         */
        @FieldNameCN("个人史")
        private String individualHistory;
        /**
         * 家族史
         * @mock 无
         */
        @FieldNameCN("家族史")
        private String familyHistory;
        /**
         * 婚育史
         * @mock 无
         */
        @FieldNameCN("婚育史")
        private String marriageHistory;
		/**
		 * 月经史
		 */
		@FieldNameCN("月经史")
        private String gynecologicalHistory;
        /**
         * 其它数据
         */
        private Map<String,String> extra = new HashMap<>();

        /**
         * 即往史推荐数量
         */
        private Integer topk;
    }
    /**
     * 是否有西医诊断
     * @return
     */
    public boolean hasWestDiag(){
        if (CollectionUtils.isEmpty(diagnoses)){
            return false;
        }
        long count = diagnoses.stream().filter(item -> "1".equals(item.getDiagnosisType())).count();
        return count > 0;
    }

    /**
     * 是否有中医诊断
     * @return
     */
    public boolean hasEastDiag(){
        if (CollectionUtils.isEmpty(diagnoses)){
            return false;
        }
        long count = diagnoses.stream().filter(item -> "2".equals(item.getDiagnosisType())).count();
        return count > 0;
    }

    /**
     * 是否有中医症候
     * @return
     */
    public boolean hasEastSyndrome(){
        if (CollectionUtils.isEmpty(diagnoses)){
            return false;
        }
        long count = diagnoses.stream().filter(item -> "3".equals(item.getDiagnosisType())).count();
        return count > 0;
    }
}
