package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnFrequent;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnFrequentMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnFrequentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则条件多发(WmkgReasonCdnFrequent)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:54
 */
@Service("wmkgReasonCdnFrequentService")
public class WmkgReasonCdnFrequentServiceImpl extends ServiceImpl<WmkgReasonCdnFrequentMapper, WmkgReasonCdnFrequent> implements WmkgReasonCdnFrequentService {
	@Resource
	private WmkgReasonCdnFrequentMapper wmkgReasonCdnFrequentMapper;


}
