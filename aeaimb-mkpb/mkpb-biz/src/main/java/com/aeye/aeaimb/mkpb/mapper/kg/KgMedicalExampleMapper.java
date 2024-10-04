package com.aeye.aeaimb.mkpb.mapper.kg;

import com.aeye.cdss.admin.api.dto.KgMedicalExampleDTO;
import com.aeye.cdss.admin.api.entity.KgMedicalExample;
import com.aeye.cdss.admin.api.vo.CaseSampleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KgMedicalExampleMapper extends BaseMapper<KgMedicalExample> {

	public IPage<KgMedicalExampleDTO> getUniversalPaging(@Param("page") Page page, @Param("caseSampleVO") CaseSampleVO caseSampleVO);
}