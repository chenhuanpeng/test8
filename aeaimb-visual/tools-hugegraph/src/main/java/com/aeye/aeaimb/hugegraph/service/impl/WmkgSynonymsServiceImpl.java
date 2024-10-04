package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSynonymsDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSynonymsErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgSynonyms;
import com.aeye.aeaimb.hugegraph.mapper.WmkgSynonymsMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgSynonymsService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * [西医]同义词(WmkgSynonyms)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-17 13:33:24
 */
@Service("wmkgSynonymsService")
public class WmkgSynonymsServiceImpl extends ServiceImpl<WmkgSynonymsMapper, WmkgSynonyms> implements WmkgSynonymsService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<WmkgSynonymsErrorDto> initSynonyms(List<WmkgSynonymsDto> schemaDtoList) {
		List<WmkgSynonyms> wmkgSynonymsList = new ArrayList<>();
		for (WmkgSynonymsDto schemaDto : schemaDtoList){
			String targetName = schemaDto.getTargetName();
			String[] targetNames = targetName.split("、");
			for (String target : targetNames){
				WmkgSynonyms wmkgSynonyms = new WmkgSynonyms();
				BeanUtil.copyProperties(schemaDto, wmkgSynonyms);
				wmkgSynonyms.setTargetName(target);
				wmkgSynonyms.setSynonymsType("同义");
				wmkgSynonymsList.add(wmkgSynonyms);
			}
		}
		this.saveBatch(wmkgSynonymsList);
		return null;
	}
}
