package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgServiceItemDict;
import com.aeye.aeaimb.hugegraph.mapper.WmkgServiceItemDictMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgServiceItemDictService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]服务项目指标字典(WmkgServiceItemDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-09-06 11:09:50
 */
@Service("wmkgServiceItemDictService")
public class WmkgServiceItemDictServiceImpl extends ServiceImpl<WmkgServiceItemDictMapper, WmkgServiceItemDict> implements WmkgServiceItemDictService {
	@Resource
	private WmkgServiceItemDictMapper wmkgServiceItemDictMapper;


}
