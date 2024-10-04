package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnPhysicalMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnPhysical;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnPhysicalService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则体格检查(WmkgReasonCdnPhysical)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:22
 */
@Service("wmkgReasonCdnPhysicalService")
@DS("wmkg")
public class WmkgReasonCdnPhysicalServiceImpl extends ServiceImpl<WmkgReasonCdnPhysicalMapper, WmkgReasonCdnPhysical> implements WmkgReasonCdnPhysicalService {
    @Resource
    private WmkgReasonCdnPhysicalMapper wmkgReasonCdnPhysicalMapper;


}
