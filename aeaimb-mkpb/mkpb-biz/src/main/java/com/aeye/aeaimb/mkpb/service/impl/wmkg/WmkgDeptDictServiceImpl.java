package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.constant.CommonConstants;
import com.aeye.aeaimb.common.core.constant.enums.CommonEnum;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDeptDictMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDeptDict;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDeptDictService;
import com.aeye.cdss.admin.api.entity.BaseDeptDict;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * [西医]科室字典(WmkgDeptDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
@Service("wmkgDeptDictService")
@DS("wmkg")
public class WmkgDeptDictServiceImpl extends ServiceImpl<WmkgDeptDictMapper, WmkgDeptDict> implements WmkgDeptDictService {

	@Override
	public List<Tree<String>> selectTree(String name) {
		// 查询全部部门

		List<WmkgDeptDict> list = this.list(Wrappers.<WmkgDeptDict>lambdaQuery()
				.like(StringUtils.isNotBlank(name), WmkgDeptDict::getDeptName, name));

		// 权限内部门
		List<TreeNode<String>> collect = list.stream()
				.filter(dept -> !dept.getDeptCode().equals(dept.getParentCode()))
				.map(dept -> {
					TreeNode<String> treeNode = new TreeNode();
					treeNode.setId(dept.getDeptCode());

					if (StringUtils.isBlank(dept.getParentCode())){
						dept.setParentCode(CommonConstants.MENU_TREE_ROOT_ID+"");
					}
					treeNode.setParentId(dept.getParentCode());
					treeNode.setName(dept.getDeptName());
					// 有权限不返回标识
					Map<String, Object> extra = new HashMap<>(8);
					extra.put("createTime", new Date());
					treeNode.setExtra(extra);
					return treeNode;
				})
				.collect(Collectors.toList());

		// 模糊查询 不组装树结构 直接返回 表格方便编辑
		if (StrUtil.isNotBlank(name)) {
			return collect.stream().map(node -> {
				Tree<String> tree = new Tree<>();
				tree.putAll(node.getExtra());
				BeanUtils.copyProperties(node, tree);
				return tree;
			}).collect(Collectors.toList());
		}

		return TreeUtil.build(collect, CommonConstants.MENU_TREE_ROOT_ID+"");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateDeptDict(WmkgDeptDict entity,String addFlag) {
		//校验唯一性
		validateDeptCode(entity, addFlag);

		this.saveOrUpdate(entity);
	}

	@Override
	public void removeDeptById(String id) {
		long count = this.count(Wrappers.<WmkgDeptDict>lambdaQuery()
				.eq(WmkgDeptDict::getParentCode, id));
		if (count > 0) {
			throw BusinessException.create(SystemMessage.DEPT_DICT_DELETE_VALIDATE);
		}

		this.removeById(id);
	}

	public void validateDeptCode(WmkgDeptDict entity, String addFlag) {
		if (CommonEnum.YesOrNoStr.YES.getType().equals(addFlag)){
			long count = this.count(Wrappers.<WmkgDeptDict>lambdaQuery()
					.eq(WmkgDeptDict::getDeptCode, entity.getDeptCode()));
			if (count > 0) {
				throw BusinessException.create(SystemMessage.DEPT_DICT_CODE_VALIDATE);
			}

			long acount = this.count(Wrappers.<WmkgDeptDict>lambdaQuery()
					.eq(WmkgDeptDict::getDeptName, entity.getDeptName()));
			if (acount > 0) {
				throw BusinessException.create(SystemMessage.DEPT_DICT_NAME_VALIDATE);
			}
		}else {
			List<WmkgDeptDict> list = this.list(Wrappers.<WmkgDeptDict>lambdaQuery()
					.eq(WmkgDeptDict::getDeptCode, entity.getDeptCode()));
			if (CollUtil.isEmpty(list)){
				throw BusinessException.create(SystemMessage.DEPT_DICT_NO_VALIDATE);
			}
		}

	}


	@Override
	public WmkgDeptDict queryDeptDict(String deptCode){
		WmkgDeptDict deptDict = this.getById(deptCode);
		return deptDict;
	}


}
