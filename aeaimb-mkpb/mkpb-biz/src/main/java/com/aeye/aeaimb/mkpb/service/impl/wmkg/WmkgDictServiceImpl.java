package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDict;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDictItem;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDictMapper;
import com.aeye.aeaimb.mkpb.vo.wmkg.DictInfo;
import com.aeye.aeaimb.mkpb.vo.wmkg.DictItemInfo;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDictItemService;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDictService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * wmkg_dict(WmkgDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
@Service("wmkgDictService")
@Slf4j
@DS("wmkg")
public class WmkgDictServiceImpl extends ServiceImpl<WmkgDictMapper, WmkgDict> implements WmkgDictService {
	@Resource
	private WmkgDictMapper wmkgDictMapper;

	@Resource
	private WmkgDictItemService wmkgDictItemService;


	/**
	 * 根据ID 删除字典
	 * @param ids 字典ID 列表
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R removeDictByIds(String[] ids) {
		//获取字典ID
		List<String> dictIdList = wmkgDictMapper.selectBatchIds(CollUtil.toList(ids))
				.stream()
				.map(WmkgDict::getId)
				.collect(Collectors.toList());
		//删除字典
		baseMapper.deleteBatchIds(dictIdList);
		//删除字典项
		wmkgDictItemService.remove(Wrappers.<WmkgDictItem>lambdaQuery().in(WmkgDictItem::getDictId, dictIdList));
		return R.ok();
	}

	/**
	 * 更新字典
	 * @param dict 字典
	 * @return
	 */
	@Override
	public R updateDict(WmkgDict dict) {
		this.updateById(dict);
		return R.ok(dict);
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
}
