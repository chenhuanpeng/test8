package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDrugCategory;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDrugCategoryMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDrugCategoryService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]药品分类表(WmkgDrugCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Service("wmkgDrugCategoryService")
@DS("wmkg")
public class WmkgDrugCategoryServiceImpl extends ServiceImpl<WmkgDrugCategoryMapper, WmkgDrugCategory> implements WmkgDrugCategoryService {
    @Resource
    private WmkgDrugCategoryMapper wmkgDrugCategoryMapper;


}
