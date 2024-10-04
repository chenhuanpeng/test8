package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseLabel;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDiseaseLabelMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseLabelService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 疾病标签表(WmkgDiseaseLabel)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:30
 */
@Service("wmkgDiseaseLabelService")
public class WmkgDiseaseLabelServiceImpl extends ServiceImpl<WmkgDiseaseLabelMapper, WmkgDiseaseLabel> implements WmkgDiseaseLabelService {
	@Resource
	private WmkgDiseaseLabelMapper wmkgDiseaseLabelMapper;


}
