package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.entity.cstn.CstnCondition;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPathPurpose;
import com.aeye.aeaimb.mkpb.vo.cstn.ConditionVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]条件 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */

@Mapper
@DS("wmkg")
public interface CstnConditionMapper extends BaseMapper<CstnCondition> {

	List<ConditionVO> getConditionList(@Param("refId") String refId, @Param("refType") String refType,
	                                   @Param("condiType") String condiType);


	int deleteConditionByRefIdAndRefType(@Param("refId") String refId, @Param("refType") String refType);



}
