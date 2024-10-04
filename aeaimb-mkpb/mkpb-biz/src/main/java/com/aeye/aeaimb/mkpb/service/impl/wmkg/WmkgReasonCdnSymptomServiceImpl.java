package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnSymptomMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnSymptom;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnSymptomService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则条件症状(WmkgReasonCdnSymptom)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Service("wmkgReasonCdnSymptomService")
@DS("wmkg")
public class WmkgReasonCdnSymptomServiceImpl extends ServiceImpl<WmkgReasonCdnSymptomMapper, WmkgReasonCdnSymptom> implements WmkgReasonCdnSymptomService {
    @Resource
    private WmkgReasonCdnSymptomMapper wmkgReasonCdnSymptomMapper;


}
