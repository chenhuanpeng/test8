package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDrugMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDrugMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.BaseDrugMappingMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseDrugMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDrugDict;
import com.aeye.aeaimb.hugegraph.service.BaseDrugMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgDrugDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 医院药品映射(BaseDrugMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-22 18:07:37
 */
@Service("baseDrugMappingService")
@Slf4j
public class BaseDrugMappingServiceImpl extends ServiceImpl<BaseDrugMappingMapper, BaseDrugMapping> implements BaseDrugMappingService {
    @Resource
    private BaseDrugMappingMapper baseDrugMappingMapper;
	@Resource
	private WmkgDrugDictService baseDrugDictService;

	@Override
	public List<BaseDrugMappingErrorDto> batchSaveDrugMapping(List<BaseDrugMappingDto> dtoList) {
		List<BaseDrugMappingErrorDto> errorDtoList = new java.util.ArrayList<>();
		log.info("药品映射数据初始化开始");
		int i = 1;
		for (BaseDrugMappingDto dto : dtoList){
			log.info("药品映射数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			WmkgDrugDict one = baseDrugDictService.getOne(Wrappers.<WmkgDrugDict>lambdaQuery()
					.eq(WmkgDrugDict::getDrugCode, dto.getDrugCode())
					.eq(WmkgDrugDict::getDrugName, dto.getDrugName()));
			if (Objects.isNull(one)){
				BaseDrugMappingErrorDto errorDto =  new BaseDrugMappingErrorDto();
				BeanUtil.copyProperties(dto, errorDto);
				errorDto.setErrorMsg("未找到对应的药品编码:"+ JSON.toJSONString(dto));
				errorDtoList.add(errorDto);
				continue;
			}
			BaseDrugMapping drugDictMapping = new BaseDrugMapping();
			drugDictMapping.setOrgId("0");
			drugDictMapping.setDrugCode(dto.getDrugCode());
			drugDictMapping.setDrugName(dto.getDrugName());
			drugDictMapping.setOrgDrugCode(dto.getOrgDrugCode());
			drugDictMapping.setOrgDrugName(dto.getOrgDrugName());
			drugDictMapping.setOrgDrugCompany(dto.getOrgDrugCompany());
			drugDictMapping.setOrgDrugSpec(dto.getOrgDrugSpec());
			drugDictMapping.setCreateBy("imp");
			drugDictMapping.setUpdateBy("imp");
			drugDictMapping.setCreateTime(new java.util.Date());
			drugDictMapping.setUpdateTime(new java.util.Date());
			baseDrugMappingMapper.insert(drugDictMapping);
		}
		log.info("药品映射数据初始化结束");
		return errorDtoList;
	}
}
