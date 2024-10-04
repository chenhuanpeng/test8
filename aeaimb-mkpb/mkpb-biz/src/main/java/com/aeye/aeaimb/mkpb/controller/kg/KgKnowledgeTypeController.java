package com.aeye.aeaimb.mkpb.controller.kg;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aeye.cdss.admin.api.entity.KgKnowledgeType;
import org.springframework.security.access.prepost.PreAuthorize;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 知识类型表
 *
 * @author lideng
 * @date 2023-12-06 17:13:37
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/kgKnowledgeType")
@Tag(description = "kgKnowledgeType", name = "知识类型表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgKnowledgeTypeController {

	private final KgKnowledgeTypeService kgKnowledgeTypeService;


	/**
	 * 分页查询
	 *
	 * @param page            分页对象
	 * @param kgKnowledgeType 知识类型表
	 * @return
	 */

	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeType_view')" )
	public R getKgKnowledgeTypePage(@ParameterObject Page page, @ParameterObject KgKnowledgeType kgKnowledgeType) {
		LambdaQueryWrapper<KgKnowledgeType> wrapper = Wrappers.lambdaQuery();
		return R.ok(kgKnowledgeTypeService.page(page, wrapper));
	}


	/**
	 * 通过id查询知识类型表
	 *
	 * @param typeCode        id
	 * @param kgKnowledgeType 知识类型表
	 * @param kgKnowledgeType 知识类型表
	 * @param ids             typeCode列表
	 * @param kgKnowledgeType 查询条件
	 * @param ids             导出指定ID
	 * @return R
	 * <p>
	 * 新增知识类型表
	 * @return R
	 * <p>
	 * 修改知识类型表
	 * @return R
	 * <p>
	 * 通过id删除知识类型表
	 * @return R
	 * <p>
	 * 导出excel 表格
	 * @return excel 文件流
	 * @ignore
	 */

	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{typeCode}")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeType_view')" )
	public R getById(@PathVariable("typeCode") String typeCode) {
		return R.ok(kgKnowledgeTypeService.getById(typeCode));
	}


	/**
	 * 新增知识类型表
	 *
	 * @param kgKnowledgeType 知识类型表
	 * @return R
	 */

	@Operation(summary = "新增知识类型表", description = "新增知识类型表")
	@SysLog("新增知识类型表")
	@PostMapping
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeType_add')" )
	public R save(@RequestBody KgKnowledgeType kgKnowledgeType) {
		return R.ok(kgKnowledgeTypeService.save(kgKnowledgeType));
	}


	/**
	 * 修改知识类型表
	 *
	 * @param kgKnowledgeType 知识类型表
	 * @return R
	 */

	@Operation(summary = "修改知识类型表", description = "修改知识类型表")
	@SysLog("修改知识类型表")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('admin_kgKnowledgeType_edit')" )
	public R updateById(@RequestBody KgKnowledgeType kgKnowledgeType) {
		return R.ok(kgKnowledgeTypeService.updateById(kgKnowledgeType));
	}


	/**
	 * 通过id删除知识类型表
	 *
	 * @param ids typeCode列表
	 * @return R
	 */

	@Operation(summary = "通过id删除知识类型表", description = "通过id删除知识类型表")
	@SysLog("通过id删除知识类型表")
	@DeleteMapping
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeType_del')" )
	public R removeById(@RequestBody String[] ids) {
		return R.ok(kgKnowledgeTypeService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param kgKnowledgeType 查询条件
	 * @param ids             导出指定ID
	 * @return excel 文件流
	 * @ignore
	 */

	@ResponseExcel
	@GetMapping("/export")
	//@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeType_export')" )
	public List<KgKnowledgeType> export(KgKnowledgeType kgKnowledgeType, String[] ids) {
		return kgKnowledgeTypeService.list(Wrappers.lambdaQuery(kgKnowledgeType).in(ArrayUtil.isNotEmpty(ids), KgKnowledgeType::getTypeCode, ids));
	}
}
