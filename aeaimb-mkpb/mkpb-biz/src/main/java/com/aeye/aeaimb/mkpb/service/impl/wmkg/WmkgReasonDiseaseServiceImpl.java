package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonDiseaseMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDisease;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonDiseaseService;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推理疾病(WmkgReasonDisease)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Service("wmkgReasonDiseaseService")
@DS("wmkg")
public class WmkgReasonDiseaseServiceImpl extends ServiceImpl<WmkgReasonDiseaseMapper, WmkgReasonDisease> implements WmkgReasonDiseaseService {

	@Override
	public List<WmkgReasonDisease> getReasonDisease(String name) {
        return this.list(Wrappers.<WmkgReasonDisease>lambdaQuery()
				.like(StringUtils.isNotBlank(name), WmkgReasonDisease::getDiseaseName, name)
						.or()
				.like(StringUtils.isNotBlank(name), WmkgReasonDisease::getDiseaseCode, name));
	}
}
