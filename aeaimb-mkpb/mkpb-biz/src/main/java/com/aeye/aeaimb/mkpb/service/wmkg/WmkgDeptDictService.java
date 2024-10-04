package com.aeye.aeaimb.mkpb.service.wmkg;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDeptDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]科室字典(WmkgDeptDict)表服务接口
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
public interface WmkgDeptDictService extends IService<WmkgDeptDict> {

	List<Tree<String>> selectTree(String name);

	void saveOrUpdateDeptDict(WmkgDeptDict entity, String addFlag);

	void removeDeptById(String id);

	WmkgDeptDict queryDeptDict(String deptCode);
}
