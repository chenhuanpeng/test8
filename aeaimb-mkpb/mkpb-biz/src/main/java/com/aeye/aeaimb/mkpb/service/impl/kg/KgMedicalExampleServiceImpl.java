package com.aeye.aeaimb.mkpb.service.impl.kg;

import com.aeye.aeaimb.mkpb.mapper.kg.KgMedicalExampleMapper;
import com.aeye.aeaimb.mkpb.service.kg.KgMedicalExampleService;
import com.aeye.cdss.admin.api.dto.KgMedicalExampleDTO;
import com.aeye.cdss.admin.api.entity.KgMedicalExample;
import com.aeye.cdss.admin.api.vo.CaseSampleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * 病历样本表
 *
 * @author cdss
 * @date 2023-12-19 15:39:00
 */
@Service
public class KgMedicalExampleServiceImpl extends ServiceImpl<KgMedicalExampleMapper, KgMedicalExample> implements KgMedicalExampleService {
	@Override
	public IPage<KgMedicalExampleDTO> getUniversalPaging(Page page, CaseSampleVO caseSampleVO) {

		return this.baseMapper.getUniversalPaging(page,caseSampleVO);
	}
}