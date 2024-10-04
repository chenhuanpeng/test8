package com.aeye.aeaimb.mkpb.controller.kg;

import com.aeye.aeaimb.mkpb.service.kg.KgTagService;
import com.aeye.cdss.admin.api.dto.TagNode;
import com.aeye.aeaimb.common.core.util.R;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *
 * @author lideng
 * @date 2023-12-27 10:24:43
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kgTag" )
@Tag(description = "kgTag" , name = "管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgTagController {

    private final KgTagService kgTagService;


	/**
	 * 返回树形菜单集合
	 * @param type 数据类型"T1"
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public R<List<TagNode>> getTree(String type) {
		return R.ok(kgTagService.getTreeStructure(type));
	}

//
//    /**
//     * 分页查询
//     * @param page 分页对象
//     * @param kgTag
//     * @return
//     */
//    @Operation(summary = "分页查询" , description = "分页查询" )
//    @GetMapping("/page" )
//    //@PreAuthorize("@pms.hasPermission('admin_kgTag_view')" )
//    public R getKgTagPage(@ParameterObject Page page, @ParameterObject KgTag kgTag) {
//        LambdaQueryWrapper<KgTag> wrapper = Wrappers.lambdaQuery();
//        return R.ok(kgTagService.page(page, wrapper));
//    }
//
//
//    /**
//     * 通过id查询
//     * @param id id
//     * @return R
//     */
//    @Operation(summary = "通过id查询" , description = "通过id查询" )
//    @GetMapping("/{id}" )
//    //@PreAuthorize("@pms.hasPermission('admin_kgTag_view')" )
//    public R getById(@PathVariable("id" ) String id) {
//        return R.ok(kgTagService.getById(id));
//    }
//
//    /**
//     * 新增
//     * @param kgTag
//     * @return R
//     */
//    @Operation(summary = "新增" , description = "新增" )
//    @SysLog("新增" )
//    @PostMapping
//    //@PreAuthorize("@pms.hasPermission('admin_kgTag_add')" )
//    public R save(@RequestBody KgTag kgTag) {
//        return R.ok(kgTagService.save(kgTag));
//    }
//
//    /**
//     * 修改
//     * @param kgTag
//     * @return R
//     */
//    @Operation(summary = "修改" , description = "修改" )
//    @SysLog("修改" )
//    @PutMapping
//   // @PreAuthorize("@pms.hasPermission('admin_kgTag_edit')" )
//    public R updateById(@RequestBody KgTag kgTag) {
//        return R.ok(kgTagService.updateById(kgTag));
//    }
//
//    /**
//     * 通过id删除
//     * @param ids id列表
//     * @return R
//     */
//    @Operation(summary = "通过id删除" , description = "通过id删除" )
//    @SysLog("通过id删除" )
//    @DeleteMapping
//    //@PreAuthorize("@pms.hasPermission('admin_kgTag_del')" )
//    public R removeById(@RequestBody String[] ids) {
//        return R.ok(kgTagService.removeBatchByIds(CollUtil.toList(ids)));
//    }
//
//
//    /**
//     * 导出excel 表格
//     * @param kgTag 查询条件
//   	 * @param ids 导出指定ID
//     * @return excel 文件流
//	 * @ignore
//     */
//    @ResponseExcel
//    @GetMapping("/export")
//   //@PreAuthorize("@pms.hasPermission('admin_kgTag_export')" )
//    public List<KgTag> export(KgTag kgTag,String[] ids) {
//        return kgTagService.list(Wrappers.lambdaQuery(kgTag).in(ArrayUtil.isNotEmpty(ids), KgTag::getId, ids));
//    }
}
