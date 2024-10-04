package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseServiceMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseServiceMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.BaseServiceMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 医院服务项目映射(BaseServiceMapping)表服务接口
 *
 * @author linkeke
 * @since 2024-07-22 17:16:05
 */
public interface BaseServiceMappingService extends IService<BaseServiceMapping> {

	List<BaseServiceMappingErrorDto> batchSaveServiceMapping(List<BaseServiceMappingDto> dtoList);
}
