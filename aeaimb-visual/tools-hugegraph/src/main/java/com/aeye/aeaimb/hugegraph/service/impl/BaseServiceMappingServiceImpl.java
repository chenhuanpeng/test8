package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseServiceMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseServiceMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.BaseServiceMappingMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseServiceMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgServiceDict;
import com.aeye.aeaimb.hugegraph.service.BaseServiceMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgServiceDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 医院服务项目映射(BaseServiceMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-22 17:16:05
 */
@Service("baseServiceMappingService")
@Slf4j
public class BaseServiceMappingServiceImpl extends ServiceImpl<BaseServiceMappingMapper, BaseServiceMapping> implements BaseServiceMappingService {
	@Resource
	private BaseServiceMappingMapper baseServiceMappingMapper;

	@Resource
	private WmkgServiceDictService wmkgServiceDictService;

	@Override
	public List<BaseServiceMappingErrorDto> batchSaveServiceMapping(List<BaseServiceMappingDto> dtoList) {

		this.getBaseMapper().delete(Wrappers.lambdaQuery());

		List<BaseServiceMappingErrorDto> errorDtoList = new java.util.ArrayList<>();
		log.info("医疗服务项目映射数据初始化开始");
		int i = 1;
		for (BaseServiceMappingDto dto : dtoList){
			log.info("医疗服务项目映射数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			WmkgServiceDict one = wmkgServiceDictService.getOne(Wrappers.<WmkgServiceDict>lambdaQuery()
					.eq(WmkgServiceDict::getServiceCode, dto.getServiceCode())
					.eq(WmkgServiceDict::getServiceName, dto.getServiceName()));
			if (Objects.isNull(one)){
				BaseServiceMappingErrorDto errorDto =  new BaseServiceMappingErrorDto();
				BeanUtil.copyProperties(dto, errorDto);
				errorDto.setErrorMsg("未找到对应的医疗服务项目编码:"+ JSON.toJSONString(dto));
				errorDtoList.add(errorDto);
				continue;
			}
			BaseServiceMapping baseServiceMapping = new BaseServiceMapping();
			baseServiceMapping.setOrgId("0");
			baseServiceMapping.setServiceCode(dto.getServiceCode());
			baseServiceMapping.setServiceName(dto.getServiceName());
			baseServiceMapping.setServiceType(dto.getServiceType());
			baseServiceMapping.setOrgServiceCode(dto.getOrgServiceCode());
			baseServiceMapping.setOrgServiceName(dto.getOrgServiceName());
			baseServiceMapping.setCreateBy("imp");
			baseServiceMapping.setUpdateBy("imp");
			baseServiceMapping.setCreateTime(new java.util.Date());
			baseServiceMapping.setUpdateTime(new java.util.Date());
			baseServiceMappingMapper.insert(baseServiceMapping);
		}
		log.info("医疗服务项目映射数据初始化结束");
		return errorDtoList;
	}
}
