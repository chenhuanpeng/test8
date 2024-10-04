package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseDiseaseMapping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 医院疾病映射(BaseDiseaseMapping)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
public interface BaseDiseaseMappingService extends IService<BaseDiseaseMapping> {

	List<BaseDiseaseMappingErrorDto> batchSaveDiseaseMapping(List<BaseDiseaseMappingDto> dtoList);
}
