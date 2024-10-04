package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonLabtestItem;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonLabtestItemMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonLabtestItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理检验指标(WmkgReasonLabtestItem)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:50
 */
@Service("wmkgReasonLabtestItemService")
public class WmkgReasonLabtestItemServiceImpl extends ServiceImpl<WmkgReasonLabtestItemMapper, WmkgReasonLabtestItem> implements WmkgReasonLabtestItemService {
	@Resource
	private WmkgReasonLabtestItemMapper wmkgReasonLabtestItemMapper;


}
