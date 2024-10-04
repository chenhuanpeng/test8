package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnSympElmtMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnSympElmt;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnSympElmtService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则条件症状要素(WmkgReasonCdnSympElmt)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:22
 */
@Service("wmkgReasonCdnSympElmtService")
@DS("wmkg")
public class WmkgReasonCdnSympElmtServiceImpl extends ServiceImpl<WmkgReasonCdnSympElmtMapper, WmkgReasonCdnSympElmt> implements WmkgReasonCdnSympElmtService {
    @Resource
    private WmkgReasonCdnSympElmtMapper wmkgReasonCdnSympElmtMapper;


}
