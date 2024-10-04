package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPathPurpose;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPathVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]路径目标 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnPathPurposeMapper extends BaseMapper<CstnPathPurpose> {

	List<CstnPathPurpose> getCstnPathPurposeList(@Param("pathId") String pathId,@Param("purposeId") String purposeId);

    int deleteByPathId(@Param("pathId") String pathId);

	Long getPurposeQtyByPathId(@Param("pathId") String pathId);

	int deletePathPurpose(@Param("pathId") String pathId, @Param("purposeId") String purposeId);


}
