package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.SysDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SysDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SysDictItemDto;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDictMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDict;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDictItem;
import com.aeye.aeaimb.hugegraph.service.WmkgDictItemService;
import com.aeye.aeaimb.hugegraph.service.WmkgDictService;
import com.aeye.aeaimb.hugegraph.service.domain.DictInfo;
import com.aeye.aeaimb.hugegraph.service.domain.DictItemInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字典表(SysDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-19 18:42:20
 */
@Service("wmkgDictService")
@Slf4j
public class WmkgDictServiceImpl extends ServiceImpl<WmkgDictMapper, WmkgDict> implements WmkgDictService {
    @Resource
    private WmkgDictItemService wmkgDictItemService;

	@Override
	public List<SysDictErrorDto> batchSysDict(List<SysDictDto> schemaDtoList, List<SysDictItemDto> sysDictItemDtos) {

		//删除基础字典数据
		this.getBaseMapper().delete(Wrappers.lambdaQuery());
		wmkgDictItemService.getBaseMapper().delete(Wrappers.lambdaQuery());
		log.info("基础字典数据初始化开始");
		int i = 1;
		for (SysDictDto schemaDto :schemaDtoList){
			log.info("基础字典数据初始化数据行数为:" + i + "条，总行数为:" + schemaDtoList.size());
			i++;
			WmkgDict wmkgDict = new WmkgDict();
			wmkgDict.setDictType(schemaDto.getDictType());
			wmkgDict.setDescription(schemaDto.getDescription());
			wmkgDict.setCreateTime(new Date());
			wmkgDict.setUpdateTime(new Date());
			wmkgDict.setCreateBy("imp");
			wmkgDict.setUpdateBy("imp");

			this.save(wmkgDict);

			for (SysDictItemDto sysDictItemDto :sysDictItemDtos){
				if (sysDictItemDto.getDictType().equals(schemaDto.getDictType())){
					WmkgDictItem sysDictItem = new WmkgDictItem();
					sysDictItem.setDictId(wmkgDict.getId());
					sysDictItem.setDictType(sysDictItemDto.getDictType());
					sysDictItem.setItemValue(sysDictItemDto.getItemValue());
					sysDictItem.setLabel(sysDictItemDto.getLabel());
					sysDictItem.setDescription(sysDictItemDto.getDescription());
					sysDictItem.setCreateTime(new Date());
					sysDictItem.setUpdateTime(new Date());
					sysDictItem.setCreateBy("imp");
					sysDictItem.setUpdateBy("imp");
					wmkgDictItemService.save(sysDictItem);
				}
			}
		}

		log.info("基础字典数据初始化结束");
		return null;
	}

	@Override
	public DictInfo getSysDictInfo(String dictCode) {
		//查询字典表
		WmkgDict one = this.getOne(Wrappers.<WmkgDict>lambdaQuery()
				.eq(WmkgDict::getDictType, dictCode)
				.or()
				.eq(WmkgDict::getDescription, dictCode));
		if (Objects.isNull(one)){
			log.info("字典表查询结果为空");
			return null;
		}
		DictInfo dictInfo = new DictInfo();
		dictInfo.setId(one.getId());
		dictInfo.setDictType(one.getDictType());
		dictInfo.setDescription(one.getDescription());

		//查询字典项表
		List<WmkgDictItem> list = wmkgDictItemService.list(Wrappers.<WmkgDictItem>lambdaQuery()
				.eq(WmkgDictItem::getDictId, one.getId()));
		if (CollUtil.isEmpty(list)){
			log.info("字典项表查询结果为空");
			return dictInfo;
		}

		List<DictItemInfo> dictItemInfos = BeanConvertUtil.convertListBean(list, DictItemInfo.class);
		dictInfo.setDictItemList(dictItemInfos);
		return dictInfo;
	}

	@Override
	public Boolean existInDict(String dictCode, String value) {
		if (Objects.isNull(dictCode) || Objects.isNull(value)){
			return false;
		}
		DictInfo sysDictInfo = getSysDictInfo(dictCode);
		List<DictItemInfo> dictItemList = sysDictInfo.getDictItemList();
		if (CollUtil.isEmpty(dictItemList)){
			return false;
		}
		List<DictItemInfo> collect = dictItemList.stream()
				.filter(dictItemInfo -> dictItemInfo.getLabel().equals(value))
				.collect(Collectors.toList());
        return !CollUtil.isEmpty(collect);
    }
}
