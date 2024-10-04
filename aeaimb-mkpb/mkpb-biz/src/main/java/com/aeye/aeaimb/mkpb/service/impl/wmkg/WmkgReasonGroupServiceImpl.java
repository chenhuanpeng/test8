package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonGroupMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonGroup;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonGroupService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则组(WmkgReasonGroup)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
@Service("wmkgReasonGroupService")
@DS("wmkg")
public class WmkgReasonGroupServiceImpl extends ServiceImpl<WmkgReasonGroupMapper, WmkgReasonGroup> implements WmkgReasonGroupService {
    @Resource
    private WmkgReasonGroupMapper wmkgReasonGroupMapper;


}
