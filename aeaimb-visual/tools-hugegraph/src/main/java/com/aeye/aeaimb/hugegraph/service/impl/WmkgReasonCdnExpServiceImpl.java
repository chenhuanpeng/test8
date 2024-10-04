package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnExp;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnExpMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnExpService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理表达式(WmkgReasonCdnExp)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:54
 */
@Service("wmkgReasonCdnExpService")
public class WmkgReasonCdnExpServiceImpl extends ServiceImpl<WmkgReasonCdnExpMapper, WmkgReasonCdnExp> implements WmkgReasonCdnExpService {
    @Resource
    private WmkgReasonCdnExpMapper wmkgReasonCdnExpMapper;


}
