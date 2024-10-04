package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.constant.enums.CommonEnum;
import com.aeye.aeaimb.hugegraph.controller.WmkgServiceDictItmtDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictBasicTreatmentDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.CheckTestDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.WmkgServiceDictMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDictKgMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgServiceDict;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgServiceItemDict;
import com.aeye.aeaimb.hugegraph.service.WmkgDictKgMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgServiceDictService;
import com.aeye.aeaimb.hugegraph.service.WmkgServiceItemDictService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * [西医]服务项目字典(WmkgServiceDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-22 11:54:45
 */
@Service("wmkgServiceDictService")
@Slf4j
public class WmkgServiceDictServiceImpl extends ServiceImpl<WmkgServiceDictMapper, WmkgServiceDict> implements WmkgServiceDictService {

	@Resource
	private WmkgDictKgMappingService wmkgDictKgMappingService;

	@Resource
	private WmkgServiceItemDictService wmkgServiceItemDictService;
	@Override
	public List<WmkgServiceDictErrorDto> batchServiceDict(List<WmkgServiceDictDto> dtoList) {
		List<WmkgServiceDictErrorDto> errorList = new ArrayList<>();
		log.info("医疗服务项目数据初始化开始");
		int i = 1;
		for (WmkgServiceDictDto dictDto : dtoList) {
			log.info("医疗服务项目数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;

			WmkgServiceDict one = this.getOne(Wrappers.<WmkgServiceDict>lambdaQuery()
					.eq(WmkgServiceDict::getServiceCode, dictDto.getServiceCode())
					.eq(WmkgServiceDict::getServiceName, dictDto.getServiceName()));
			if (Objects.nonNull(one)){
				WmkgServiceDictErrorDto errorDto = new WmkgServiceDictErrorDto();
				errorDto.setErrorMsg("医疗服务项目数据初始化数据重复:+"+ JSON.toJSONString(dictDto));
				errorList.add(errorDto);
				continue;
			}

			WmkgServiceDict dict = new WmkgServiceDict();
			dict.setServiceCode(dictDto.getServiceCode());
			dict.setServiceName(dictDto.getServiceName());
			dict.setServiceType(dictDto.getServiceType());
			dict.setServiceBasic(CommonEnum.YesOrNoStr.NO.getType());
			dict.setCreateBy("imp");
			dict.setCreateTime(new java.util.Date());
			dict.setUpdateBy("imp");
			dict.setUpdateTime(new java.util.Date());
			this.save(dict);
		}
		log.info("医疗服务项目数据初始化完毕");
		return errorList;
	}

	@Override
	public List<WmkgServiceDictErrorDto> batchServiceDictBasic(List<WmkgServiceDictBasicTreatmentDto> dtoList) {
		List<WmkgServiceDictErrorDto> errorList = new ArrayList<>();
		log.info("医疗服务基础项目数据初始化开始");
		int i = 1;
		for (WmkgServiceDictBasicTreatmentDto dictDto : dtoList) {
			log.info("医疗服务基础项目数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			LambdaUpdateWrapper<WmkgServiceDict> updateWrapper = Wrappers.<WmkgServiceDict>lambdaUpdate()
					.set(WmkgServiceDict::getServiceBasic, CommonEnum.YesOrNoStr.YES.getType())
					.eq(WmkgServiceDict::getServiceCode, dictDto.getServiceCode())
					.eq(WmkgServiceDict::getServiceName, dictDto.getServiceName());
			this.update(updateWrapper);
		}
		log.info("医疗服务基础项目数据初始化完毕");
		return errorList;
	}

	@Override
	public List<WmkgServiceDictErrorDto> batchServiceDictKgMapping(List<CheckTestDictKgMappingDto> dtoList) {

		log.info("检查检验映射知识库数据初始化开始");
		int i = 1;
		for (CheckTestDictKgMappingDto dictDto : dtoList) {
			log.info("检查检验映射知识库数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			String id = dictDto.getId();
			if (StrUtil.isBlank(id)){
				log.info(dictDto.getServiceCode() +"检查检验映射知识库数据为空");
				continue;
			}
			WmkgDictKgMapping dictKgMapping = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
					.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getServiceCode())
					.eq(WmkgDictKgMapping::getKgId, id));
			if (Objects.isNull(dictKgMapping)){
				WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
				baseDictKgMapping.setMediDictCode(dictDto.getServiceCode());
				baseDictKgMapping.setKgId(id);
				baseDictKgMapping.setKgName(dictDto.getName());
				if ("检验".equals(dictDto.getType())){
					baseDictKgMapping.setKgType("T9");
				}
				if ("检查".equals(dictDto.getType())){
					baseDictKgMapping.setKgType("T8");
				}
				baseDictKgMapping.setMediDictName(dictDto.getServiceName());
				baseDictKgMapping.setCreateBy("imp");
				baseDictKgMapping.setUpdateBy("imp");
				baseDictKgMapping.setCreateTime(new Date());
				baseDictKgMapping.setUpdateTime(new Date());
				wmkgDictKgMappingService.save(baseDictKgMapping);
			}
		}
		log.info("检查检验映射知识库数据初始化结束");
		return null;
	}

	@Override
	public List<WmkgServiceDictErrorDto> batchServiceItemDict(List<WmkgServiceDictItmtDto> dtoList) {
		log.info("检查检验指标数据初始化开始");
		int i = 1;
		for (WmkgServiceDictItmtDto dictDto : dtoList) {
			log.info("检查检验指标数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			WmkgServiceItemDict itemDict = wmkgServiceItemDictService.getOne(Wrappers.<WmkgServiceItemDict>lambdaQuery()
					.eq(WmkgServiceItemDict::getItemCode, dictDto.getItemCode())
					.eq(WmkgServiceItemDict::getItemName, dictDto.getItemName()));
			if (Objects.isNull(itemDict)){
				WmkgServiceItemDict baseItemDict = new WmkgServiceItemDict();
				baseItemDict.setItemCode(dictDto.getItemCode());
				baseItemDict.setItemName(dictDto.getItemName());
				wmkgServiceItemDictService.save(baseItemDict);
			}
		}
		log.info("检查检验指标初始化结束");
		return null;
	}
}
