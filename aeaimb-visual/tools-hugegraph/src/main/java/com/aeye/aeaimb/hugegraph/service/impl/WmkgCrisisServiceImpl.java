package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.hugegraph.constant.CommonConstants;
import com.aeye.aeaimb.hugegraph.controller.dto.CriticalValueDto;
import com.aeye.aeaimb.hugegraph.controller.dto.CriticalValueErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.WmkgCrisisMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgCrisis;
import com.aeye.aeaimb.hugegraph.service.WmkgCrisisService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * [西医]危机值(WmkgCrisis)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-19 17:22:42
 */
@Service("wmkgCrisisService")
public class WmkgCrisisServiceImpl extends ServiceImpl<WmkgCrisisMapper, WmkgCrisis> implements WmkgCrisisService {

	@Override
	public List<CriticalValueErrorDto> batchSaveCriticalValue(List<CriticalValueDto> criticalValueDtoList) {
		List<CriticalValueErrorDto> errorList = new ArrayList<>();
		JexlBuilder jexlBuilder = new JexlBuilder();
		JexlEngine jexlEngine = jexlBuilder.create();
		List<WmkgCrisis> wmkgCrises = new ArrayList<>();
		for (CriticalValueDto criticalValueDto :criticalValueDtoList){
			String indicatorName = criticalValueDto.getIndicatorName();
			String indicatorValue = criticalValueDto.getIndicatorValue();
			if (StrUtil.isNotBlank(indicatorValue) && !CommonConstants.dingXing.contains(indicatorName)){
				//校验定量值
				try {
					jexlEngine.createExpression(indicatorValue);
				} catch (Exception e) {
					CriticalValueErrorDto errorDto = new CriticalValueErrorDto();
					BeanUtil.copyProperties(criticalValueDto, errorDto);
					errorDto.setErrorMsg("指标值表达式错误：" + indicatorValue);
					errorList.add(errorDto);
					continue;
				}

			}

			String suitAge = criticalValueDto.getSuitAge();
			if (StrUtil.isNotBlank(suitAge)){
				//校验定量值
				try {
					jexlEngine.createExpression(suitAge);
				} catch (Exception e) {
					CriticalValueErrorDto errorDto = new CriticalValueErrorDto();
					BeanUtil.copyProperties(criticalValueDto, errorDto);
					errorDto.setErrorMsg("适用年龄（天）错误：" + suitAge);
					errorList.add(errorDto);
					continue;
				}
			}
			wmkgCrises.add(BeanConvertUtil.convertBean(criticalValueDto, WmkgCrisis.class));
		}
		this.saveBatch(wmkgCrises);
		return errorList;
	}
}
