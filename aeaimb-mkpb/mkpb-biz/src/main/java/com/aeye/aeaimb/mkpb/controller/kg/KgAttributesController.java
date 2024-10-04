package com.aeye.aeaimb.mkpb.controller.kg;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.service.kg.KgAttributesService;
import com.aeye.cdss.admin.api.entity.KgAttributes;
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
 * 知识属性
 *
 * @author lideng
 * @date 2023-12-06 17:14:07
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/kgAttributes" )
@Tag(description = "kgAttributes" , name = "知识属性管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgAttributesController {

    private final KgAttributesService kgAttributesService;


/**
     * 分页查询
     * @param page 分页对象
     * @param kgAttributes 知识属性
     * @return
     */

    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    //@PreAuthorize("@pms.hasPermission('admin_kgAttributes_view')" )
    public R getKgAttributesPage(@ParameterObject Page page, @ParameterObject KgAttributes kgAttributes) {
        LambdaQueryWrapper<KgAttributes> wrapper = Wrappers.lambdaQuery();
        return R.ok(kgAttributesService.page(page, wrapper));
    }



/**
     * 通过id查询知识属性
     * @param id id
     * @return R
     */

    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    //@PreAuthorize("@pms.hasPermission('admin_kgAttributes_view')" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(kgAttributesService.getById(id));
    }


/**
     * 新增知识属性
     * @param kgAttributes 知识属性
     * @return R
     */

    @Operation(summary = "新增知识属性" , description = "新增知识属性" )
    @SysLog("新增知识属性" )
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgAttributes_add')" )
    public R save(@RequestBody KgAttributes kgAttributes) {
        return R.ok(kgAttributesService.save(kgAttributes));
    }


/**
     * 修改知识属性
     * @param kgAttributes 知识属性
     * @return R
     */

    @Operation(summary = "修改知识属性" , description = "修改知识属性" )
    @SysLog("修改知识属性" )
    @PutMapping
   // @PreAuthorize("@pms.hasPermission('admin_kgAttributes_edit')" )
    public R updateById(@RequestBody KgAttributes kgAttributes) {
        return R.ok(kgAttributesService.updateById(kgAttributes));
    }


/**
     * 通过id删除知识属性
     * @param ids id列表
     * @return R
     */

    @Operation(summary = "通过id删除知识属性" , description = "通过id删除知识属性" )
    @SysLog("通过id删除知识属性" )
    @DeleteMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgAttributes_del')" )
    public R removeById(@RequestBody String[] ids) {
        return R.ok(kgAttributesService.removeBatchByIds(CollUtil.toList(ids)));
    }



/**
     * 导出excel 表格
     * @param kgAttributes 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
	 * @ignore
     */

    @ResponseExcel
    @GetMapping("/export")
   //@PreAuthorize("@pms.hasPermission('admin_kgAttributes_export')" )
    public List<KgAttributes> export(KgAttributes kgAttributes,String[] ids) {
        return kgAttributesService.list(Wrappers.lambdaQuery(kgAttributes).in(ArrayUtil.isNotEmpty(ids), KgAttributes::getId, ids));
    }
}
