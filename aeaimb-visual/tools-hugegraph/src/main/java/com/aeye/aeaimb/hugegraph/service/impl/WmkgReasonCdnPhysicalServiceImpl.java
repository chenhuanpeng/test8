package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnPhysical;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnPhysicalMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnPhysicalService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则体格检查(WmkgReasonCdnPhysical)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:53
 */
@Service("wmkgReasonCdnPhysicalService")
public class WmkgReasonCdnPhysicalServiceImpl extends ServiceImpl<WmkgReasonCdnPhysicalMapper, WmkgReasonCdnPhysical> implements WmkgReasonCdnPhysicalService {
	@Resource
	private WmkgReasonCdnPhysicalMapper wmkgReasonCdnPhysicalMapper;


}
