package com.aeye.aeaimb.mkpb.controller.kg;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.mkpb.service.kg.KgKnowledgeAsocTagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.cdss.admin.api.entity.KgKnowledgeAsocTag;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author lid
 * @date 2023-12-27 11:10:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kgKnowledgeAsocTag" )
@Tag(description = "kgKnowledgeAsocTag" , name = "管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgKnowledgeAsocTagController {

    private final KgKnowledgeAsocTagService kgKnowledgeAsocTagService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param kgKnowledgeAsocTag
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeAsocTag_view')" )
    public R getKgKnowledgeAsocTagPage(@ParameterObject Page page, @ParameterObject KgKnowledgeAsocTag kgKnowledgeAsocTag) {
        LambdaQueryWrapper<KgKnowledgeAsocTag> wrapper = Wrappers.lambdaQuery();
        return R.ok(kgKnowledgeAsocTagService.page(page, wrapper));
    }


    /**
     * 通过id查询
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeAsocTag_view')" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(kgKnowledgeAsocTagService.getById(id));
    }

    /**
     * 新增
     * @param kgKnowledgeAsocTag
     * @return R
     */
    @Operation(summary = "新增" , description = "新增" )
    @SysLog("新增" )
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeAsocTag_add')" )
    public R save(@RequestBody KgKnowledgeAsocTag kgKnowledgeAsocTag) {
        return R.ok(kgKnowledgeAsocTagService.save(kgKnowledgeAsocTag));
    }

    /**
     * 修改
     * @param kgKnowledgeAsocTag
     * @return R
     */
    @Operation(summary = "修改" , description = "修改" )
    @SysLog("修改" )
    @PutMapping
   // @PreAuthorize("@pms.hasPermission('admin_kgKnowledgeAsocTag_edit')" )
    public R updateById(@RequestBody KgKnowledgeAsocTag kgKnowledgeAsocTag) {
        return R.ok(kgKnowledgeAsocTagService.updateById(kgKnowledgeAsocTag));
    }

    /**
     * 通过id删除
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除" , description = "通过id删除" )
    @SysLog("通过id删除" )
    @DeleteMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeAsocTag_del')" )
    public R removeById(@RequestBody String[] ids) {
        return R.ok(kgKnowledgeAsocTagService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param kgKnowledgeAsocTag 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
	 * @ignore
     */
    @ResponseExcel
    @GetMapping("/export")
   //@PreAuthorize("@pms.hasPermission('admin_kgKnowledgeAsocTag_export')" )
    public List<KgKnowledgeAsocTag> export(KgKnowledgeAsocTag kgKnowledgeAsocTag,String[] ids) {
        return kgKnowledgeAsocTagService.list(Wrappers.lambdaQuery(kgKnowledgeAsocTag).in(ArrayUtil.isNotEmpty(ids), KgKnowledgeAsocTag::getId, ids));
    }
}
