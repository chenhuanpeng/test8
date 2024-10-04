package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonGroup;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonGroupMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonGroupService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则组(WmkgReasonGroup)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:50
 */
@Service("wmkgReasonGroupService")
public class WmkgReasonGroupServiceImpl extends ServiceImpl<WmkgReasonGroupMapper, WmkgReasonGroup> implements WmkgReasonGroupService {
	@Resource
	private WmkgReasonGroupMapper wmkgReasonGroupMapper;


}
