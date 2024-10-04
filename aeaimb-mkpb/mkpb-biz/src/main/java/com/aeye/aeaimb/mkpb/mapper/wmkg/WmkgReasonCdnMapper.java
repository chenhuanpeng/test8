package com.aeye.aeaimb.mkpb.mapper.wmkg;

import com.aeye.aeaimb.mkpb.dto.cdn.exam.AddReasonCdnExamDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.multi.AddReasonCdnFrequentDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.symptom.AddReasonCdnSympElmtDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.test.AddReasonCdnLabtestDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 推理规则条件(WmkgReasonCdn)表数据库访问层
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Mapper
public interface WmkgReasonCdnMapper extends BaseMapper<WmkgReasonCdn> {

	List<AddReasonCdnSympElmtDTO> getReasonCdnSymptom(@Param("groupIds") List<String> groupIds);
	List<AddReasonCdnFrequentDTO> getReasonCdnFrequent(@Param("groupIds") List<String> groupIds);

	List<AddReasonCdnFrequentDTO> getReasonCdnPhysical(@Param("groupIds") List<String> groupIds);

	List<AddReasonCdnExamDTO> getReasonCdnExam(@Param("groupIds") List<String> groupIds);

	List<AddReasonCdnLabtestDTO> getReasonCdnLabtest(@Param("groupIds") List<String> groupIds);

}

