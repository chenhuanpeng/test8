package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonLabtest;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonLabtestMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonLabtestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理检验(WmkgReasonLabtest)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:50
 */
@Service("wmkgReasonLabtestService")
public class WmkgReasonLabtestServiceImpl extends ServiceImpl<WmkgReasonLabtestMapper, WmkgReasonLabtest> implements WmkgReasonLabtestService {
    @Resource
    private WmkgReasonLabtestMapper wmkgReasonLabtestMapper;


}
