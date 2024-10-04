package com.aeye.aeaimb.mkpb.controller.kg;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeFieldService;
import com.aeye.cdss.admin.api.entity.KgKnowledgeField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 知识字段配置表
 *
 * @author lideng
 * @date 2023-12-06 17:13:49
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/kgKnowledgeField")
@Tag(description = "kgKnowledgeField", name = "知识字段配置表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgKnowledgeFieldController {

	private final KgKnowledgeFieldService kgKnowledgeFieldService;


	/**
	 * 分页查询
	 *
	 * @param page             分页对象
	 * @param kgKnowledgeField 知识字段配置表
	 * @return
	 */

	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeField_view')" )
	public R getKgKnowledgeFieldPage(@ParameterObject Page page, @ParameterObject KgKnowledgeField kgKnowledgeField) {
		LambdaQueryWrapper<KgKnowledgeField> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeFieldService.page(page, wrapper));
	}


	/**
	 * 通过id查询知识字段配置表
	 *
	 * @param fieldCode id
	 * @return R
	 */

	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{fieldCode}")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeField_view')" )
	public R getById(@PathVariable("fieldCode") String fieldCode) {
		return R.ok(kgKnowledgeFieldService.getById(fieldCode));
	}


	/**
	 * 新增知识字段配置表
	 *
	 * @param kgKnowledgeField 知识字段配置表
	 * @return R
	 */

	@Operation(summary = "新增知识字段配置表", description = "新增知识字段配置表")
	@SysLog("新增知识字段配置表")
	@PostMapping
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeField_add')" )
	public R save(@RequestBody KgKnowledgeField kgKnowledgeField) {
		return R.ok(kgKnowledgeFieldService.save(kgKnowledgeField));
	}


	/**
	 * 修改知识字段配置表
	 *
	 * @param kgKnowledgeField 知识字段配置表
	 * @return R
	 */

	@Operation(summary = "修改知识字段配置表", description = "修改知识字段配置表")
	@SysLog("修改知识字段配置表")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('admin_kgKnowledgeField_edit')" )
	public R updateById(@RequestBody KgKnowledgeField kgKnowledgeField) {
		return R.ok(kgKnowledgeFieldService.updateById(kgKnowledgeField));
	}


	/**
	 * 通过id删除知识字段配置表
	 *
	 * @param ids fieldCode列表
	 * @return R
	 */

	@Operation(summary = "通过id删除知识字段配置表", description = "通过id删除知识字段配置表")
	@SysLog("通过id删除知识字段配置表")
	@DeleteMapping
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeField_del')" )
	public R removeById(@RequestBody String[] ids) {
		return R.ok(kgKnowledgeFieldService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param kgKnowledgeField 查询条件
	 * @param ids              导出指定ID
	 * @return excel 文件流
	 * @ignore
	 */

	@ResponseExcel
	@GetMapping("/export")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeField_export')" )
	public List<KgKnowledgeField> export(KgKnowledgeField kgKnowledgeField, String[] ids) {
		return kgKnowledgeFieldService.list(Wrappers.lambdaQuery(kgKnowledgeField).in(ArrayUtil.isNotEmpty(ids), KgKnowledgeField::getFieldCode, ids));
	}
}
