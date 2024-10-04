package com.aeye.aeaimb.mkpb.controller.wmkg;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDeptDict;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDeptDictService;
import com.aeye.cdss.admin.api.dto.dict.BaseDeptDictDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 标准科室字典
 *
 * @author linkeke
 * @since 2024-08-22 08:40:42
 */
@RestController
@RequestMapping("deptDict")
public class WmkgDeptDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDeptDictService wmkgDeptDictService;

	/**
	 * 标准科室树接口
	 * @param name 节点名称
	 * @return 标准科室树对象
	 */
	@GetMapping(value = "/tree")
	public R<List<Tree<String>>> getTree(String name) {
		return R.ok(wmkgDeptDictService.selectTree(name));
	}


	/**
	 * 添加或更新标准科室字典
	 * @param baseDeptDictDTO 标准科室字典
	 * @return false true
	 */
	@SysLog("添加或更新标准科室字典")
	@PostMapping("saveOrUpdate")
	public R<Boolean> save(@Valid @RequestBody BaseDeptDictDTO baseDeptDictDTO) {
		WmkgDeptDict deptDict = BeanConvertUtil.convertBean(baseDeptDictDTO, WmkgDeptDict.class);
		wmkgDeptDictService.saveOrUpdateDeptDict(deptDict,baseDeptDictDTO.getAddFlag());
		return R.ok();
	}


	@SysLog("删除标准科室字典")
	@GetMapping("deleteById")
	public R<Boolean> removeById(@RequestParam(name = "id") @Valid @NotBlank(message = "id参数不能为空") String id) {
		wmkgDeptDictService.removeDeptById(id);
		return R.ok();
	}


}

