package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.CriticalValueDto;
import com.aeye.aeaimb.hugegraph.controller.dto.CriticalValueErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgCrisis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]危机值(WmkgCrisis)表服务接口
 *
 * @author linkeke
 * @since 2024-07-19 17:22:40
 */
public interface WmkgCrisisService extends IService<WmkgCrisis> {

	List<CriticalValueErrorDto> batchSaveCriticalValue(List<CriticalValueDto> criticalValueDtoList);
}
