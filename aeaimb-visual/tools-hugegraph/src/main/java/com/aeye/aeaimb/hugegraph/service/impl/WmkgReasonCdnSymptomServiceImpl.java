package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnSymptom;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnSymptomMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则条件症状(WmkgReasonCdnSymptom)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:53
 */
@Service("wmkgReasonCdnSymptomService")
public class WmkgReasonCdnSymptomServiceImpl extends ServiceImpl<WmkgReasonCdnSymptomMapper, WmkgReasonCdnSymptom> implements WmkgReasonCdnSymptomService {
    @Resource
    private WmkgReasonCdnSymptomMapper wmkgReasonCdnSymptomMapper;


}
