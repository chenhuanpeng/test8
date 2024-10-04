package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonExamItem;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonExamItemMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonExamItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理检查指标(WmkgReasonExamItem)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:51
 */
@Service("wmkgReasonExamItemService")
public class WmkgReasonExamItemServiceImpl extends ServiceImpl<WmkgReasonExamItemMapper, WmkgReasonExamItem> implements WmkgReasonExamItemService {
	@Resource
	private WmkgReasonExamItemMapper wmkgReasonExamItemMapper;


}
