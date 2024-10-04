package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDictItem;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDictItemMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDictItemService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * wmkg_dict_item(WmkgDictItem)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Service("wmkgDictItemService")
@DS("wmkg")
public class WmkgDictItemServiceImpl extends ServiceImpl<WmkgDictItemMapper, WmkgDictItem> implements WmkgDictItemService {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return
	 */
	@Override
	public R removeDictItem(String id) {
		return R.ok(this.removeById(id));
	}

	/**
	 * 更新字典项
	 * @param item 字典项
	 * @return
	 */
	@Override
	public R updateDictItem(WmkgDictItem item) {
		return R.ok(this.updateById(item));
	}


}
