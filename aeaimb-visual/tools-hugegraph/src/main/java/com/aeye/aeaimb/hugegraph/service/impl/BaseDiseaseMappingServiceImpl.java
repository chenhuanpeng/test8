package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.BaseDiseaseMappingMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseDiseaseMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseDict;
import com.aeye.aeaimb.hugegraph.service.BaseDiseaseMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 医院疾病映射(BaseDiseaseMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Service("baseDiseaseMappingService")
@Slf4j
public class BaseDiseaseMappingServiceImpl extends ServiceImpl<BaseDiseaseMappingMapper, BaseDiseaseMapping> implements BaseDiseaseMappingService {
	@Resource
	private BaseDiseaseMappingMapper baseDiseaseMappingMapper;
	@Resource
	private WmkgDiseaseDictService baseDiseaseDictService;

	@Override
	public List<BaseDiseaseMappingErrorDto> batchSaveDiseaseMapping(List<BaseDiseaseMappingDto> dtoList) {
		List<BaseDiseaseMappingErrorDto> errorDtoList = new java.util.ArrayList<>();
		log.info("疾病映射数据初始化开始");
		int i = 1;
		for (BaseDiseaseMappingDto dto : dtoList){
			log.info("疾病映射数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			WmkgDiseaseDict one = baseDiseaseDictService.getOne(Wrappers.<WmkgDiseaseDict>lambdaQuery()
					.eq(WmkgDiseaseDict::getDiseaseCode, dto.getDiseaseCode())
					.eq(WmkgDiseaseDict::getNormType, dto.getNormType())
					.eq(WmkgDiseaseDict::getDiseaseName, dto.getDiseaseName()));
			if (Objects.isNull(one)){
				BaseDiseaseMappingErrorDto baseDiseaseMappingErrorDto = new BaseDiseaseMappingErrorDto();
				BeanUtil.copyProperties(dto, baseDiseaseMappingErrorDto);
				baseDiseaseMappingErrorDto.setErrorMsg("未找到对应的疾病编码:"+ JSON.toJSONString(dto));
				errorDtoList.add(baseDiseaseMappingErrorDto);
				continue;
			}
			BaseDiseaseMapping baseDiseaseMapping = new BaseDiseaseMapping();
			baseDiseaseMapping.setOrgId("0");
			baseDiseaseMapping.setDiseaseCode(dto.getDiseaseCode());
			baseDiseaseMapping.setDiseaseName(dto.getDiseaseName());
			baseDiseaseMapping.setNormType(dto.getNormType());
			baseDiseaseMapping.setOrgDiseaseCode(dto.getOrgDiseaseCode());
			baseDiseaseMapping.setOrgDiseaseName(dto.getOrgDiseaseName());
			baseDiseaseMapping.setCreateBy("imp");
			baseDiseaseMapping.setUpdateBy("imp");
			baseDiseaseMapping.setCreateTime(new java.util.Date());
			baseDiseaseMapping.setUpdateTime(new java.util.Date());
			baseDiseaseMappingMapper.insert(baseDiseaseMapping);
		}
		log.info("疾病映射数据初始化结束");
		return errorDtoList;
	}
}
