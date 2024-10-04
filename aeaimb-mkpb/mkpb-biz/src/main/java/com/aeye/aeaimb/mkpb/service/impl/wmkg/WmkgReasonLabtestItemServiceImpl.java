package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonLabtestItemMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtestItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonLabtestItemService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理检验指标(WmkgReasonLabtestItem)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
@Service("wmkgReasonLabtestItemService")
@DS("wmkg")
public class WmkgReasonLabtestItemServiceImpl extends ServiceImpl<WmkgReasonLabtestItemMapper, WmkgReasonLabtestItem> implements WmkgReasonLabtestItemService {
    @Resource
    private WmkgReasonLabtestItemMapper wmkgReasonLabtestItemMapper;


}
