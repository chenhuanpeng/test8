package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSymptomDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSymptomDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.SymptomDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgSymptomDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 症状字典(BaseSymptomDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 15:39:50
 */
public interface WmkgSymptomDictService extends IService<WmkgSymptomDict> {

	List<WmkgSymptomDictErrorDto> batchSymptomDict(List<WmkgSymptomDictDto> dtoList);
	List<WmkgSymptomDictErrorDto> batchSymptomDictKgMapping(List<SymptomDictKgMappingDto> dtoList);
}
