package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.WmkgServiceDictItmtDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictBasicTreatmentDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.CheckTestDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgServiceDict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]服务项目字典(WmkgServiceDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-22 11:54:45
 */
public interface WmkgServiceDictService extends IService<WmkgServiceDict> {

	List<WmkgServiceDictErrorDto> batchServiceDict(List<WmkgServiceDictDto> dtoList);
	List<WmkgServiceDictErrorDto> batchServiceDictBasic(List<WmkgServiceDictBasicTreatmentDto> dtoList);
	List<WmkgServiceDictErrorDto> batchServiceDictKgMapping(List<CheckTestDictKgMappingDto> dtoList);

	List<WmkgServiceDictErrorDto> batchServiceItemDict(List<WmkgServiceDictItmtDto> dtoList);
}
