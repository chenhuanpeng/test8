package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgGraphMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgGraphMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgGraphMapping;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 图谱标准字典映射(BaseGraphMapping)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 15:58:03
 */
public interface WmkgGraphMappingService extends IService<WmkgGraphMapping> {

	List<WmkgGraphMappingErrorDto> batchGraphMapping(List<WmkgGraphMappingDto> dtoList);
}
