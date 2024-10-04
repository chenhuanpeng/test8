package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictLabelDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DiseaseDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 疾病字典(BaseDiseaseDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
public interface WmkgDiseaseDictService extends IService<WmkgDiseaseDict> {

	List<WmkgDiseaseDictErrorDto> batchDiseaseDict(List<WmkgDiseaseDictDto> dictDtos);
	List<WmkgDiseaseDictErrorDto> batchDiseaseDictLabel(List<WmkgDiseaseDictLabelDto> dictDtos);
	List<WmkgDiseaseDictErrorDto> batchDiseaseDictKgMapping(List<DiseaseDictKgMappingDto> dictDtos);

	WmkgDiseaseDict getDiseaseDict(String diseaseName);
}
