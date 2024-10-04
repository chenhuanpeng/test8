package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseInCategory;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDiseaseInCategoryMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseInCategoryService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 疾病所属分类(BaseDiseaseInCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Service("baseDiseaseInCategoryService")
public class WmkgDiseaseInCategoryServiceImpl extends ServiceImpl<WmkgDiseaseInCategoryMapper, WmkgDiseaseInCategory> implements WmkgDiseaseInCategoryService {
	@Resource
	private WmkgDiseaseInCategoryMapper baseDiseaseInCategoryMapper;


}
