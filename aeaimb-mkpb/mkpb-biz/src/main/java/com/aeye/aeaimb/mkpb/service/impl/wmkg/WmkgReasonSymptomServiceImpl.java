package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonSymptomMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonSymptom;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonSymptomService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推理症状(WmkgReasonSymptom)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:44:52
 */
@Service("wmkgReasonSymptomService")
@DS("wmkg")
public class WmkgReasonSymptomServiceImpl extends ServiceImpl<WmkgReasonSymptomMapper, WmkgReasonSymptom> implements WmkgReasonSymptomService {

	@Override
	public List<WmkgReasonSymptom> getReasonSymptom(String name) {
        return this.list(Wrappers.<WmkgReasonSymptom>lambdaQuery().like(StrUtil.isNotBlank(name), WmkgReasonSymptom::getSymptomName, name));
	}
}
