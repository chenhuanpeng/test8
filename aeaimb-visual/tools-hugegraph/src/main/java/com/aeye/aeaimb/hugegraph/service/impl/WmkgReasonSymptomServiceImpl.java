package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonSymptom;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonSymptomMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonSymptomService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理症状(WmkgReasonSymptom)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:51
 */
@Service("wmkgReasonSymptomService")
public class WmkgReasonSymptomServiceImpl extends ServiceImpl<WmkgReasonSymptomMapper, WmkgReasonSymptom> implements WmkgReasonSymptomService {
	@Resource
	private WmkgReasonSymptomMapper wmkgReasonSymptomMapper;


}
