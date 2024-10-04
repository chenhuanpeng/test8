package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.dto.cstn.MetaDataDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnMeta;
import com.aeye.aeaimb.mkpb.vo.cstn.CommonVO;
import com.aeye.aeaimb.mkpb.vo.cstn.MetaDataVO;
import com.aeye.cdss.admin.api.entity.pojo.BaseDepartmentPOJO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * <p>
 * [问诊]问诊条件元数据 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnMetaMapper extends BaseMapper<CstnMeta> {

	List<MetaDataVO> getMetaDataList();

	List<MetaDataDTO>  queryMetaDataList(@Param("bitFlag") String bitFlag);

	@Select("${dynamicSql}")
	List<CommonVO> querySql(@Param("dynamicSql") String dynamicSql);

}
