package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.entity.cstn.CstnPathDept;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestionOption;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]路径科室 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnPathDeptMapper extends BaseMapper<CstnPathDept> {

	int deleteCstnPathDeptList(@Param("pathId") String pathId);

	List<CstnPathDept> getCstnPathDeptList(@Param("pathId") String pathId);

}
