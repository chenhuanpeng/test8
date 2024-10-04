package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseSpecial;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDiseaseSpecialMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseSpecialService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 疾病特殊处理建议(WmkgDiseaseSpecial)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:30
 */
@Service("wmkgDiseaseSpecialService")
public class WmkgDiseaseSpecialServiceImpl extends ServiceImpl<WmkgDiseaseSpecialMapper, WmkgDiseaseSpecial> implements WmkgDiseaseSpecialService {
	@Resource
	private WmkgDiseaseSpecialMapper wmkgDiseaseSpecialMapper;


}
