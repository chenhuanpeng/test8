package com.aeye.aeaimb.mkpb.service.kg;

import com.aeye.cdss.admin.api.dto.KgMedicalExampleDTO;
import com.aeye.cdss.admin.api.entity.KgMedicalExample;
import com.aeye.cdss.admin.api.vo.CaseSampleVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

@DS("cdss_knowledge")
public interface KgMedicalExampleService extends IService<KgMedicalExample> {


	public IPage<KgMedicalExampleDTO> getUniversalPaging(Page page, CaseSampleVO caseSampleVO);

}