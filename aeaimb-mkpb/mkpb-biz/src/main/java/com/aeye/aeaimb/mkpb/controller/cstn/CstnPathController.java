package com.aeye.aeaimb.mkpb.controller.cstn;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.service.cstn.CstnPathService;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPathVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathAuditVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathRelatePurposeVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [问诊]路径 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/cstn-path")
public class CstnPathController {

	@Autowired
	private CstnPathService cstnPathService;

	/**
	 * 路径列表分頁查詢
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@PostMapping("/getCstnPathPageList")
	public R<IPage<CstnPathVO>> getCstnPathPageList(@RequestBody CstnPathDTO dto){
		Page<CstnPath> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<CstnPathVO> pathPageList = cstnPathService.getCstnPathPageList(page, dto);
		return R.ok(pathPageList);
	}

	/**
	 * 分页查询
	 * @param page     分页对象
	 * @param cstnPath 问诊路径
	 * @return
	 */
	@GetMapping("/page")
	public R getCstnPathPage(Page page, CstnPath cstnPath) {
		return R.ok(cstnPathService.page(page, Wrappers.query(cstnPath)));
	}

	/**
	 * 查看
	 * @param id id
	 * @return R
	 */
	@GetMapping("/show/{id}")
	public R show(@PathVariable("id") String id) {
		return R.ok(cstnPathService.show(id));
	}

	/**
	 * 点击编辑
	 * @param id id
	 * @return R
	 */
	@GetMapping("/edit/{id}")
	public R edit(@PathVariable("id") String id) {
		return R.ok(cstnPathService.edit(id));
	}

	/**
	 * 预览
	 * @param id id
	 * @return R
	 */
	@GetMapping("/preview/{id}")
	public R preview(@PathVariable("id") String id) {
		return R.ok(cstnPathService.preview(id));
	}

	/**
	 * 提交
	 * @param id
	 * @return
	 */
	@GetMapping("/submit/{id}")
	public R submit(@PathVariable("id") String id) {
		return R.ok(cstnPathService.submit(id));
	}

	/**
	 * 上线
	 * @param id
	 * @return
	 */
	@GetMapping("/online/{id}")
	public R online(@PathVariable("id") String id) {
		return R.ok(cstnPathService.online(id));
	}

	/**
	 * 退回编辑
	 * @param id
	 * @return
	 */
	@GetMapping("/returnEdit/{id}")
	public R returnEdit(@PathVariable("id") String id) {
		return R.ok(cstnPathService.returnEdit(id));
	}

	/**
	 * 下线
	 * @param id
	 * @return
	 */
	@GetMapping("/offline/{id}")
	public R offline(@PathVariable("id") String id) {
		return R.ok(cstnPathService.offline(id));
	}

	@PostMapping("/batchOffline")
	public R batchOffline(@RequestBody String[] ids) {

		return R.ok(cstnPathService.batchOffline(ids));
	}

	@PostMapping("/batchOnline")
	public R batchOnline(@RequestBody String[] ids) {

		return R.ok(cstnPathService.batchOnline(ids));
	}

	@PostMapping("/batchAudit")
	public R batchAudit(@RequestBody PathAuditVO pathAuditVO) {

		return R.ok(cstnPathService.batchAudit(pathAuditVO));
	}


	/**
	 * 详情
	 * @param cstnPath 查询条件
	 * @return R
	 */
	@GetMapping("/query-detail")
	public R getPathDetails(CstnPath cstnPath) {
		return R.ok(cstnPathService.getOne(Wrappers.query(cstnPath), false));
	}

	/**
	 * 保存
	 *
	 * @param path 路径
	 * @return R
	 */
	@SysLog("保存路径")
	@PostMapping("/save-path")
	public R savePath(@RequestBody PathVO path) {
		return R.ok(cstnPathService.add(path));
	}

	/**
	 * 编辑路径
	 * @param path 路径
	 * @return R
	 */

	@SysLog("编辑路径")
	@PutMapping("/update-path")
	public R updatePath(@Valid @RequestBody PathVO path) {
		return R.ok(cstnPathService.update(path));
	}

	/**
	 * 删除路径
	 * @param id id
	 * @return R
	 */
	@SysLog("删除路径")
	@DeleteMapping("/del-path/{id}")
	public R removeById(@PathVariable String id) {
		return R.ok(cstnPathService.remove(id));
	}


	/**
	 * 审核
	 * @param auditVO
	 * @return
	 */
	@PostMapping("/audit")
	public R audit(@RequestBody PathAuditVO auditVO) {
		return R.ok(cstnPathService.audit(auditVO));
	}


	/**
	 * 获取所有目标树
	 * @return
	 */
	@GetMapping(value = "/tree")
	public R<List<Tree<String>>> getTree() {
		List<Tree<String>> trees = cstnPathService.selectTree();
		return R.ok(trees);
	}

	/**
	 * 获取所有科室树
	 * @return
	 */
	@GetMapping(value = "/queryAllDepartment")
	public R<List<Tree<String>>> queryAllDepartment() {
		List<Tree<String>> trees = cstnPathService.queryAllDepartment();
		return R.ok(trees);
	}

	/**
	 * 关联目标反显
	 * @param id 路径ID
	 * @return
	 */
	@GetMapping(value = "/getRelatePurposeList/{id}")
	public R<List<Tree<String>>> getRelatePurposeList(@PathVariable String id) {
		List<Tree<String>> trees = cstnPathService.getRelatePurposeList(id);
		return R.ok(trees);
	}




	/**
	 * 保存关联的目标
	 * @param relatePurposeVO
	 * @return
	 */
	@PostMapping("/saveRelatePurpose")
	public R saveRelatePurpose(@RequestBody PathRelatePurposeVO relatePurposeVO){
		return R.ok(cstnPathService.relatePurpose(relatePurposeVO));
	}




}
