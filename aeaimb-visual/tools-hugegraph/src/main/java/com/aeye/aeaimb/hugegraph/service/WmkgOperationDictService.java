package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgOperateDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgOperateDictErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgOperationDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 手术字典(BaseOperationDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 15:15:36
 */
public interface WmkgOperationDictService extends IService<WmkgOperationDict> {

	List<WmkgOperateDictErrorDto> batchOperationDict(List<WmkgOperateDictDto> dictDtos);
}
