package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDeptDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDeptDictErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDeptDict;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDeptDictMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDeptDictService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 科室字典(BaseDeptDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 11:32:44
 */
@Service("baseDeptDictService")
public class WmkgDeptDictServiceImpl extends ServiceImpl<WmkgDeptDictMapper, WmkgDeptDict> implements WmkgDeptDictService {

	@Override
	public List<WmkgDeptDictErrorDto> batchDeptDict(List<WmkgDeptDictDto> schemaDtoList) {
		List<WmkgDeptDict> wmkgDeptDicts = BeanConvertUtil.convertListBean(schemaDtoList, WmkgDeptDict.class);
		this.saveBatch(wmkgDeptDicts);
		return null;
	}
}
