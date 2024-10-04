package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonOpMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonOp;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonOpService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理手术(WmkgReasonOp)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:44:32
 */
@Service("wmkgReasonOpService")
@DS("wmkg")
public class WmkgReasonOpServiceImpl extends ServiceImpl<WmkgReasonOpMapper, WmkgReasonOp> implements WmkgReasonOpService {
    @Resource
    private WmkgReasonOpMapper wmkgReasonOpMapper;


}
