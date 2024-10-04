package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgServiceToItem;
import com.aeye.aeaimb.hugegraph.mapper.WmkgServiceToItemMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgServiceToItemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * [西医]服务项目包括指标(WmkgServiceToItem)表服务实现类
 *
 * @author linkeke
 * @since 2024-09-06 11:09:50
 */
@Service("wmkgServiceToItemService")
public class WmkgServiceToItemServiceImpl extends ServiceImpl<WmkgServiceToItemMapper, WmkgServiceToItem> implements WmkgServiceToItemService {
    @Resource
    private WmkgServiceToItemMapper wmkgServiceToItemMapper;


}
