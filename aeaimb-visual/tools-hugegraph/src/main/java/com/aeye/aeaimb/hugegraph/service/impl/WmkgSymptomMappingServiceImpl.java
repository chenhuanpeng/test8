package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgSymptomMapping;
import com.aeye.aeaimb.hugegraph.mapper.WmkgSymptomMappingMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgSymptomMappingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 症状字典映射(BaseSymptomMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:39:50
 */
@Service("baseSymptomMappingService")
public class WmkgSymptomMappingServiceImpl extends ServiceImpl<WmkgSymptomMappingMapper, WmkgSymptomMapping> implements WmkgSymptomMappingService {
	@Resource
	private WmkgSymptomMappingMapper baseSymptomMappingMapper;


}
