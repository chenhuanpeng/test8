package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseOperationMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseOperationMappingErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DiseaseDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.OperationDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.BaseOperationMappingMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseOperationMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDictKgMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgOperationDict;
import com.aeye.aeaimb.hugegraph.service.BaseOperationMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgDictKgMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgOperationDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 医院手术映射(BaseOperationMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@Service("baseOperationMappingService")
@Slf4j
public class BaseOperationMappingServiceImpl extends ServiceImpl<BaseOperationMappingMapper, BaseOperationMapping> implements BaseOperationMappingService {
	@Resource
	private BaseOperationMappingMapper baseOperationMappingMapper;

	@Resource
	private WmkgOperationDictService baseOperationDictService;

	@Resource
	private WmkgDictKgMappingService wmkgDictKgMappingService;

	@Override
	public List<BaseOperationMappingErrorDto> batchSaveOperationMapping(List<BaseOperationMappingDto> dtoList) {
		List<BaseOperationMappingErrorDto> errorDtoList = new java.util.ArrayList<>();
		log.info("手术映射数据初始化开始");
		int i = 1;
		for (BaseOperationMappingDto dto : dtoList){
			log.info("手术映射数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			WmkgOperationDict one = baseOperationDictService.getOne(Wrappers.<WmkgOperationDict>lambdaQuery()
					.eq(WmkgOperationDict::getOperationCode, dto.getOperationCode())
					.eq(WmkgOperationDict::getNormType, dto.getNormType())
					.eq(WmkgOperationDict::getOperationName, dto.getOperationName()));
			if (Objects.isNull(one)){
				BaseOperationMappingErrorDto errorDto =  new BaseOperationMappingErrorDto();
				BeanUtil.copyProperties(dto, errorDto);
				errorDto.setErrorMsg("未找到对应的手术编码:"+ JSON.toJSONString(dto));
				errorDtoList.add(errorDto);
				continue;
			}
			BaseOperationMapping baseOperationMapping = new BaseOperationMapping();
			baseOperationMapping.setOrgId("0");
			baseOperationMapping.setOperationCode(dto.getOperationCode());
			baseOperationMapping.setOperationName(dto.getOperationName());
			baseOperationMapping.setNormType(dto.getNormType());
			baseOperationMapping.setOrgOperationName(dto.getOrgOperationName());
			baseOperationMapping.setOrgOperationCode(dto.getOrgOperationCode());
			baseOperationMapping.setCreateBy("imp");
			baseOperationMapping.setUpdateBy("imp");
			baseOperationMapping.setCreateTime(new java.util.Date());
			baseOperationMapping.setUpdateTime(new java.util.Date());
			baseOperationMappingMapper.insert(baseOperationMapping);
		}
		log.info("手术映射数据初始化结束");
		return errorDtoList;
	}

	@Override
	public List<BaseOperationMappingErrorDto> batchSaveOperationKgMapping(List<OperationDictKgMappingDto> dtoList) {

		log.info("手术映射知识库数据初始化开始");
		int i = 1;
		for (OperationDictKgMappingDto dictDto : dtoList) {
			log.info("手术映射知识库数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			String id = dictDto.getId();
			if (StrUtil.isBlank(id)){
				log.info(dictDto.getOperationName() +"手术映射知识库数据为空");
				continue;
			}
			WmkgDictKgMapping dictKgMapping = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
					.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getOperationCode())
					.eq(WmkgDictKgMapping::getKgId, id));
			if (Objects.isNull(dictKgMapping)){
				WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
				baseDictKgMapping.setMediDictCode(dictDto.getOperationCode());
				baseDictKgMapping.setKgId(id);
				baseDictKgMapping.setKgName(dictDto.getName());
				baseDictKgMapping.setKgType("T7");
				baseDictKgMapping.setMediDictName(dictDto.getOperationName());
				baseDictKgMapping.setCreateBy("imp");
				baseDictKgMapping.setUpdateBy("imp");
				baseDictKgMapping.setCreateTime(new Date());
				baseDictKgMapping.setUpdateTime(new Date());
				wmkgDictKgMappingService.save(baseDictKgMapping);
			}
		}
		log.info("疾病映射知识库数据初始化结束");
		return null;
	}
}
