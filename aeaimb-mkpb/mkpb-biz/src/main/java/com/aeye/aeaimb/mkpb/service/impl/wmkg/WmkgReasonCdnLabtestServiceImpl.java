package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnLabtestMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnLabtest;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnLabtestService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则条件检验(WmkgReasonCdnLabtest)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:21
 */
@Service("wmkgReasonCdnLabtestService")
@DS("wmkg")
public class WmkgReasonCdnLabtestServiceImpl extends ServiceImpl<WmkgReasonCdnLabtestMapper, WmkgReasonCdnLabtest> implements WmkgReasonCdnLabtestService {
    @Resource
    private WmkgReasonCdnLabtestMapper wmkgReasonCdnLabtestMapper;


}
