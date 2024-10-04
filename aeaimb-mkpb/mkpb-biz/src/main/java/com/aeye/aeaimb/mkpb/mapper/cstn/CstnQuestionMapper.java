package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.dto.cstn.CstnQuestionDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestion;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnQuestionVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PurposeVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]问题 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnQuestionMapper extends BaseMapper<CstnQuestion> {


	IPage<CstnQuestionVO> getCstnQuestionPageList(Page<CstnQuestion> page, @Param("o") CstnQuestionDTO dto);

	CstnQuestion getQuestionByQaTitleAndPurposeId(@Param("qaTitle") String qaTitle,@Param("purposeId") String purposeId);

}
