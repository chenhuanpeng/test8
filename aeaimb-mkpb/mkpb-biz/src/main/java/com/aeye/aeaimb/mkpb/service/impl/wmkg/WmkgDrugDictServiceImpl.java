package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDrugDict;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDrugDictMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDrugDictService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]药品字典(WmkgDrugDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:44
 */
@Service("wmkgDrugDictService")
@DS("wmkg")
public class WmkgDrugDictServiceImpl extends ServiceImpl<WmkgDrugDictMapper, WmkgDrugDict> implements WmkgDrugDictService {
	@Resource
	private WmkgDrugDictMapper wmkgDrugDictMapper;


}
