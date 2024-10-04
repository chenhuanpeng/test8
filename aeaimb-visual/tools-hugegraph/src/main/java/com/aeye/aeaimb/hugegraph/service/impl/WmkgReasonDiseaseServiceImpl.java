package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonDisease;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonDiseaseMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonDiseaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理疾病(WmkgReasonDisease)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:28:08
 */
@Service("wmkgReasonDiseaseService")
public class WmkgReasonDiseaseServiceImpl extends ServiceImpl<WmkgReasonDiseaseMapper, WmkgReasonDisease> implements WmkgReasonDiseaseService {
	@Resource
	private WmkgReasonDiseaseMapper wmkgReasonDiseaseMapper;


}
