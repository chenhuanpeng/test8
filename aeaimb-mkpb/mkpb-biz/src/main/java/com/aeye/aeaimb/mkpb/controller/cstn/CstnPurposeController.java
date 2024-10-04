package com.aeye.aeaimb.mkpb.controller.cstn;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPurposeDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPurpose;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPurpose;
import com.aeye.aeaimb.mkpb.service.cstn.CstnPurposeService;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPurposeVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PurposeShowVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * [问诊]目标 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/cstn-purpose")
public class CstnPurposeController {

	@Autowired
	private CstnPurposeService cstnPurposeService;

	/**
	 * 目标列表分頁查詢
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@PostMapping("/getCstnPurposePageList")
	public R<IPage<CstnPurposeVO>> getCstnPurposePageList(@RequestBody CstnPurposeDTO dto){
		Page<CstnPurpose> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<CstnPurposeVO> pathPageList = cstnPurposeService.getCstnPurposePageList(page, dto);
		return R.ok(pathPageList);
	}

	/**
	 * 分页查询
	 * @param page     分页对象
	 * @param cstnPurpose 问诊目标
	 * @return
	 */
	@GetMapping("/page")
	public R getPurposePage(Page page, CstnPurpose cstnPurpose) {
		return R.ok(cstnPurposeService.page(page, Wrappers.query(cstnPurpose)));
	}

	/**
	 * 查看
	 * @param id id
	 * @return R
	 */
	@GetMapping("/show/{id}")
	public R show(@PathVariable("id") String id) {
		return R.ok(cstnPurposeService.show(id));
	}

	/**
	 * 点击进入编辑页面
	 * @param id id
	 * @return R
	 */
	@GetMapping("/edit/{id}")
	public R edit(@PathVariable("id") String id) {
		return R.ok(cstnPurposeService.edit(id));
	}

	/**
	 * 预览
	 * @param id id
	 * @return R
	 */
	@GetMapping("/preview/{id}")
	public R preview(@PathVariable("id") String id) {
		return R.ok(cstnPurposeService.preview(id));
	}

	/**
	 * 详情
	 * @param cstnPurpose 查询条件
	 * @return R
	 */
	@GetMapping("/query-detail")
	public R getPurposeDetails(CstnPurpose cstnPurpose) {
		return R.ok(cstnPurposeService.getOne(Wrappers.query(cstnPurpose), false));
	}

	/**
	 * 保存
	 *
	 * @param purpose 目标
	 * @return R
	 */
	@SysLog("保存目标")
	@PostMapping("/save-purpose")
	public R savePurpose(@RequestBody PurposeShowVO purpose) {
		return R.ok(cstnPurposeService.add(purpose));
	}

	/**
	 * 编辑目标
	 * @param purpose 目标
	 * @return R
	 */

	@SysLog("编辑目标")
	@PutMapping("/update-purpose")
	public R updatePurpose(@Valid @RequestBody PurposeShowVO purpose) {
		return R.ok(cstnPurposeService.update(purpose));
	}

	/**
	 * 删除目标
	 * @param id id
	 * @return R
	 */
	@SysLog("删除目标")
	@DeleteMapping("/del-purpose/{id}")
	public R removeById(@PathVariable String id) {
		return R.ok(cstnPurposeService.remove(id));
	}

	@GetMapping(value = "/tree")
	public R<List<Tree<String>>> getTree() {
		List<Tree<String>> trees = cstnPurposeService.selectTree();
		return R.ok(trees);
	}

	/**
	 * 获取所有主目标树
	 * @return
	 */
	@GetMapping(value = "/getParentPurposeList")
	public R<List<Tree<String>>> getParentPurposeList() {
		List<Tree<String>> trees = cstnPurposeService.getParentPurposeList();
		return R.ok(trees);
	}

}
