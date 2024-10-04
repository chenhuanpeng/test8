package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDict;
import com.aeye.aeaimb.mkpb.vo.wmkg.DictInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * wmkg_dict(WmkgDict)表服务接口
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
public interface WmkgDictService extends IService<WmkgDict> {

	/**
	 * 根据ID 删除字典
	 * @param ids ID列表
	 * @return
	 */
	R removeDictByIds(String[] ids);

	/**
	 * 更新字典
	 * @param wmkgDict 字典
	 * @return
	 */
	R updateDict(WmkgDict wmkgDict);

	DictInfo getSysDictInfo(String dictCode);
}
