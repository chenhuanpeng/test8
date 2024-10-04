package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgOperationInCategory;
import com.aeye.aeaimb.hugegraph.mapper.WmkgOperationInCategoryMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgOperationInCategoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 手术所属分类(BaseOperationInCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@Service("baseOperationInCategoryService")
public class WmkgOperationInCategoryServiceImpl extends ServiceImpl<WmkgOperationInCategoryMapper, WmkgOperationInCategory> implements WmkgOperationInCategoryService {
	@Resource
	private WmkgOperationInCategoryMapper baseOperationInCategoryMapper;


}
