package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnFrequentMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnFrequent;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnFrequentService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则条件多发(WmkgReasonCdnFrequent)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:21
 */
@Service("wmkgReasonCdnFrequentService")
@DS("wmkg")
public class WmkgReasonCdnFrequentServiceImpl extends ServiceImpl<WmkgReasonCdnFrequentMapper, WmkgReasonCdnFrequent> implements WmkgReasonCdnFrequentService {
    @Resource
    private WmkgReasonCdnFrequentMapper wmkgReasonCdnFrequentMapper;


}
