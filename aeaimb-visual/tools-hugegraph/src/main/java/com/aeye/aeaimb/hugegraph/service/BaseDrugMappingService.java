package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseDrugMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDrugMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseDrugMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 医院药品映射(BaseDrugMapping)表服务接口
 *
 * @author linkeke
 * @since 2024-07-22 18:07:37
 */
public interface BaseDrugMappingService extends IService<BaseDrugMapping> {

	List<BaseDrugMappingErrorDto> batchSaveDrugMapping(List<BaseDrugMappingDto> dtoList);
}
