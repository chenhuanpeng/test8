package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCategoryMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCategory;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCategoryService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理疾病业务分组(WmkgReasonCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Service("wmkgReasonCategoryService")
@DS("wmkg")
public class WmkgReasonCategoryServiceImpl extends ServiceImpl<WmkgReasonCategoryMapper, WmkgReasonCategory> implements WmkgReasonCategoryService {
	@Resource
	private WmkgReasonCategoryMapper wmkgReasonCategoryMapper;


}
