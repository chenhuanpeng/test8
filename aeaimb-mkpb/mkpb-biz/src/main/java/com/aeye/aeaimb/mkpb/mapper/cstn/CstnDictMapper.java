package com.aeye.aeaimb.mkpb.mapper.cstn;

import com.aeye.aeaimb.mkpb.entity.cstn.CstnDict;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestionPurpose;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]字典表 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-09-05
 */
@Mapper
@DS("wmkg")
public interface CstnDictMapper extends BaseMapper<CstnDict> {

	List<CstnDict> getDictList( @Param("dictType") String dictType);

}
