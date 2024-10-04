package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnLabtest;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnLabtestMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnLabtestService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则条件检验(WmkgReasonCdnLabtest)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:54
 */
@Service("wmkgReasonCdnLabtestService")
public class WmkgReasonCdnLabtestServiceImpl extends ServiceImpl<WmkgReasonCdnLabtestMapper, WmkgReasonCdnLabtest> implements WmkgReasonCdnLabtestService {
	@Resource
	private WmkgReasonCdnLabtestMapper wmkgReasonCdnLabtestMapper;


}
