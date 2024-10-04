package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCategory;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCategoryMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCategoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理疾病业务分组(WmkgReasonCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:56
 */
@Service("wmkgReasonCategoryService")
public class WmkgReasonCategoryServiceImpl extends ServiceImpl<WmkgReasonCategoryMapper, WmkgReasonCategory> implements WmkgReasonCategoryService {
	@Resource
	private WmkgReasonCategoryMapper wmkgReasonCategoryMapper;


}
