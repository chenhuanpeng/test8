package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdn;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则条件(WmkgReasonCdn)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:55
 */
@Service("wmkgReasonCdnService")
public class WmkgReasonCdnServiceImpl extends ServiceImpl<WmkgReasonCdnMapper, WmkgReasonCdn> implements WmkgReasonCdnService {
	@Resource
	private WmkgReasonCdnMapper wmkgReasonCdnMapper;


}
