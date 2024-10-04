package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnExpMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnExp;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnExpService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理表达式(WmkgReasonCdnExp)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:21
 */
@Service("wmkgReasonCdnExpService")
@DS("wmkg")
public class WmkgReasonCdnExpServiceImpl extends ServiceImpl<WmkgReasonCdnExpMapper, WmkgReasonCdnExp> implements WmkgReasonCdnExpService {
    @Resource
    private WmkgReasonCdnExpMapper wmkgReasonCdnExpMapper;


}
