package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.entity.cstn.CstnPathPurpose;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestionPurpose;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]问题目标 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnQuestionPurposeMapper extends BaseMapper<CstnQuestionPurpose> {

	List<CstnQuestionPurpose> getQuestionPurposeList(@Param("qaId") String qaId, @Param("purposeId") String purposeId);

	int deleteQuestionPurpose(@Param("qaId") String qaId, @Param("purposeId") String purposeId);

}
