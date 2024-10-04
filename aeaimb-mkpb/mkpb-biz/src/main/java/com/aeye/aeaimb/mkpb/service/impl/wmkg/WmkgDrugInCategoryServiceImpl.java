package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDrugInCategory;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDrugInCategoryMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDrugInCategoryService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]药品所属分类(WmkgDrugInCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:44
 */
@Service("wmkgDrugInCategoryService")
@DS("wmkg")
public class WmkgDrugInCategoryServiceImpl extends ServiceImpl<WmkgDrugInCategoryMapper, WmkgDrugInCategory> implements WmkgDrugInCategoryService {
	@Resource
	private WmkgDrugInCategoryMapper wmkgDrugInCategoryMapper;


}
