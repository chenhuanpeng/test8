package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.SysDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SysDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SysDictItemDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDict;
import com.aeye.aeaimb.hugegraph.service.domain.DictInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 字典表(SysDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-19 18:42:20
 */
public interface WmkgDictService extends IService<WmkgDict> {

	List<SysDictErrorDto> batchSysDict(List<SysDictDto> schemaDtoList, List<SysDictItemDto> sysDictItemDtos);

	DictInfo getSysDictInfo(String dictCode);

	Boolean existInDict(String dictCode,String value);
}
