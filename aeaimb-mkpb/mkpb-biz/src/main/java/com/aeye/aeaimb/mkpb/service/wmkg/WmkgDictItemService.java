package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.sys.SysDictItem;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * wmkg_dict_item(WmkgDictItem)表服务接口
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
public interface WmkgDictItemService extends IService<WmkgDictItem> {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return
	 */
	R removeDictItem(String id);

	/**
	 * 更新字典项
	 * @param item 字典项
	 * @return
	 */
	R updateDictItem(WmkgDictItem item);
}
