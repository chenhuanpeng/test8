package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonExamItemMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExamItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonExamItemService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理检查指标(WmkgReasonExamItem)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
@Service("wmkgReasonExamItemService")
@DS("wmkg")
public class WmkgReasonExamItemServiceImpl extends ServiceImpl<WmkgReasonExamItemMapper, WmkgReasonExamItem> implements WmkgReasonExamItemService {
    @Resource
    private WmkgReasonExamItemMapper wmkgReasonExamItemMapper;


}
