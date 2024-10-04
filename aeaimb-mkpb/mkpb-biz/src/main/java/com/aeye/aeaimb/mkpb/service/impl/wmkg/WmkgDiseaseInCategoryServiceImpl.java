package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDiseaseInCategory;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDiseaseInCategoryMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDiseaseInCategoryService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]疾病所属分类(WmkgDiseaseInCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Service("wmkgDiseaseInCategoryService")
@DS("wmkg")
public class WmkgDiseaseInCategoryServiceImpl extends ServiceImpl<WmkgDiseaseInCategoryMapper, WmkgDiseaseInCategory> implements WmkgDiseaseInCategoryService {
    @Resource
    private WmkgDiseaseInCategoryMapper wmkgDiseaseInCategoryMapper;


}
