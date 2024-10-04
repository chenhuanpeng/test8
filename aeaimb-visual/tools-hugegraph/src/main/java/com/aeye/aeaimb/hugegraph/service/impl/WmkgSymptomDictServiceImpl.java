package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSymptomDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSymptomDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DrugDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.SymptomDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.WmkgSymptomDictMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDictKgMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgSymptomDict;
import com.aeye.aeaimb.hugegraph.service.WmkgDictKgMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgSymptomDictService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 症状字典(BaseSymptomDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:39:50
 */
@Service("baseSymptomDictService")
@Slf4j
public class WmkgSymptomDictServiceImpl extends ServiceImpl<WmkgSymptomDictMapper, WmkgSymptomDict> implements WmkgSymptomDictService {

	@Resource
	private WmkgDictKgMappingService wmkgDictKgMappingService;

	@Override
	public List<WmkgSymptomDictErrorDto> batchSymptomDict(List<WmkgSymptomDictDto> dtoList) {

		//删除所有
		this.getBaseMapper().delete( Wrappers.lambdaQuery());


		List<WmkgSymptomDict> baseSymptomDicts = BeanConvertUtil.convertListBean(dtoList, WmkgSymptomDict.class);
		for (WmkgSymptomDict baseSymptomDict : baseSymptomDicts){
			baseSymptomDict.setCreateBy("imp");
			baseSymptomDict.setUpdateBy("imp");
			baseSymptomDict.setCreateTime(new Date());
			baseSymptomDict.setUpdateTime(new Date());
		}
		this.saveBatch(baseSymptomDicts);
		return null;
	}

	@Override
	public List<WmkgSymptomDictErrorDto> batchSymptomDictKgMapping(List<SymptomDictKgMappingDto> dtoList) {

		log.info("症状映射知识库数据初始化开始");
		int i = 1;
		for (SymptomDictKgMappingDto dictDto : dtoList) {
			log.info("症状映射知识库数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			String id = dictDto.getId();
			if (StrUtil.isBlank(id)){
				log.info(dictDto.getSymptomName() +"症状映射知识库数据为空");
				continue;
			}
			if (StrUtil.isBlank(dictDto.getSymptomCode())){
				log.info(dictDto.getSymptomName() +"症状编码数据为空");
				continue;
			}
			WmkgDictKgMapping dictKgMapping = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
					.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getSymptomCode())
					.eq(WmkgDictKgMapping::getKgId, id));
			if (Objects.isNull(dictKgMapping)){
				WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
				baseDictKgMapping.setMediDictCode(dictDto.getSymptomCode());
				baseDictKgMapping.setKgId(id);
				baseDictKgMapping.setKgName(dictDto.getName());
				baseDictKgMapping.setKgType("T5");
				baseDictKgMapping.setMediDictName(dictDto.getSymptomName());
				baseDictKgMapping.setCreateBy("imp");
				baseDictKgMapping.setUpdateBy("imp");
				baseDictKgMapping.setCreateTime(new Date());
				baseDictKgMapping.setUpdateTime(new Date());
				wmkgDictKgMappingService.save(baseDictKgMapping);
			}
		}
		log.info("症状映射知识库数据初始化结束");
		return null;
	}
}
