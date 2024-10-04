package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefinePageDTO;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDefine;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineCountVO;
import com.aeye.aeaimb.mkpb.vo.cstn.ConditionVO;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPathVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [问诊]路径 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnPathMapper extends BaseMapper<CstnPath> {

	IPage<CstnPathVO> getCstnPathPageList(Page<CstnPath> page, @Param("o") CstnPathDTO dto);


	List<PathVO> getPathList(@Param("id") String id);

}
