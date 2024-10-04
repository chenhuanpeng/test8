package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDrugGoodsMapping;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDrugGoodsMappingMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDrugGoodsMappingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]药品商品映射(WmkgDrugGoodsMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-30 09:08:05
 */
@Service("wmkgDrugGoodsMappingService")
public class WmkgDrugGoodsMappingServiceImpl extends ServiceImpl<WmkgDrugGoodsMappingMapper, WmkgDrugGoodsMapping> implements WmkgDrugGoodsMappingService {
	@Resource
	private WmkgDrugGoodsMappingMapper wmkgDrugGoodsMappingMapper;


}
