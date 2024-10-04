package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdn;
import com.aeye.aeaimb.mkpb.dto.cdn.AuReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.exam.AddReasonCdnExamDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.multi.AddReasonCdnFrequentDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.symptom.AddReasonCdnSymptomDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.test.AddReasonCdnLabtestDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 推理规则条件(WmkgReasonCdn)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
public interface WmkgReasonCdnService extends IService<WmkgReasonCdn> {


	String saveReasonCdnSymp(AuReasonDefineDTO<AddReasonCdnSymptomDTO> dto);
	AuReasonDefineDTO<AddReasonCdnSymptomDTO> getReasonCdnSymp(String reasonId);


	String saveReasonCdnMulti(AuReasonDefineDTO<AddReasonCdnFrequentDTO> dto);

	AuReasonDefineDTO<AddReasonCdnFrequentDTO> getReasonCdnMulti(String reasonId,String factorCate);

	String saveReasonCdnPhysical(AuReasonDefineDTO<AddReasonCdnFrequentDTO> dto);

	AuReasonDefineDTO<AddReasonCdnFrequentDTO> getReasonCdnPhysical(String reasonId,String factorCate);

	String saveReasonCdnExam(AuReasonDefineDTO<AddReasonCdnExamDTO> dto);

	AuReasonDefineDTO<AddReasonCdnExamDTO> getReasonCdnExam(String reasonId,String factorCate);

	String saveReasonCdnLabtest(AuReasonDefineDTO<AddReasonCdnLabtestDTO> dto);

	AuReasonDefineDTO<AddReasonCdnLabtestDTO> getReasonCdnLabtest(String reasonId,String factorCate);

}
