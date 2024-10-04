package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDeptDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDeptDictErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDeptDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 科室字典(BaseDeptDict)表服务接口
 *
 * @author linkeke
 * @since 2024-07-21 11:32:44
 */
public interface WmkgDeptDictService extends IService<WmkgDeptDict> {

	List<WmkgDeptDictErrorDto> batchDeptDict(List<WmkgDeptDictDto> schemaDtoList);
}
