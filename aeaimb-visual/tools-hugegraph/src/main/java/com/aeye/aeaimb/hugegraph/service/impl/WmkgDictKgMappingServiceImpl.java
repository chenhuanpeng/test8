package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDictKgMapping;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDictKgMappingMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDictKgMappingService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]人卫知识与医疗字典映射(WmkgDictKgMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-14 10:06:26
 */
@Service("wmkgDictKgMappingService")
public class WmkgDictKgMappingServiceImpl extends ServiceImpl<WmkgDictKgMappingMapper, WmkgDictKgMapping> implements WmkgDictKgMappingService {
    @Resource
    private WmkgDictKgMappingMapper wmkgDictKgMappingMapper;


}
