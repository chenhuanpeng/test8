package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseOperationMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseOperationMappingErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.OperationDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseOperationMapping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 医院手术映射(BaseOperationMapping)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
public interface BaseOperationMappingService extends IService<BaseOperationMapping> {

	List<BaseOperationMappingErrorDto> batchSaveOperationMapping(List<BaseOperationMappingDto> dtoList);
	List<BaseOperationMappingErrorDto> batchSaveOperationKgMapping(List<OperationDictKgMappingDto> dtoList);
}
