package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgMedInsurMapping;
import com.aeye.aeaimb.hugegraph.mapper.WmkgMedInsurMappingMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgMedInsurMappingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 医疗医保编码映射(BaseMedInsurMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:41
 */
@Service("baseMedInsurMappingService")
public class WmkgMedInsurMappingServiceImpl extends ServiceImpl<WmkgMedInsurMappingMapper, WmkgMedInsurMapping> implements WmkgMedInsurMappingService {
	@Resource
	private WmkgMedInsurMappingMapper baseMedInsurMappingMapper;


}
