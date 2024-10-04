package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DrugDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDrugDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 药品字典(BaseDrugDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
public interface WmkgDrugDictService extends IService<WmkgDrugDict> {

	List<WmkgDrugDictErrorDto> batchDrugDict(List<WmkgDrugDictDto> dtoList);
	List<WmkgDrugDictErrorDto> batchDrugDictKgMapping(List<DrugDictKgMappingDto> dtoList);
}
