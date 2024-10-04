package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonDefine;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonDefineMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonDefineService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理疾病定义(WmkgReasonDefine)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:52
 */
@Service("wmkgReasonDefineService")
public class WmkgReasonDefineServiceImpl extends ServiceImpl<WmkgReasonDefineMapper, WmkgReasonDefine> implements WmkgReasonDefineService {
	@Resource
	private WmkgReasonDefineMapper wmkgReasonDefineMapper;


}
