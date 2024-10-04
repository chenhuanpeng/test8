package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnSympElmt;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnSympElmtMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnSympElmtService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则条件症状要素(WmkgReasonCdnSympElmt)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:53
 */
@Service("wmkgReasonCdnSympElmtService")
public class WmkgReasonCdnSympElmtServiceImpl extends ServiceImpl<WmkgReasonCdnSympElmtMapper, WmkgReasonCdnSympElmt> implements WmkgReasonCdnSympElmtService {
    @Resource
    private WmkgReasonCdnSympElmtMapper wmkgReasonCdnSympElmtMapper;


}
