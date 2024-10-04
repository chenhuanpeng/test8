package com.aeye.aeaimb.mkpb.controller.kg;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.service.kg.KgMedicalDiagnosisService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aeye.cdss.admin.api.entity.KgMedicalDiagnosis;
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
 * 病历样本诊断表
 *
 * @author lid
 * @date 2023-12-19 16:09:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kgMedicalDiagnosis" )
@Tag(description = "kgMedicalDiagnosis" , name = "病历样本诊断表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgMedicalDiagnosisController {

    private final KgMedicalDiagnosisService kgMedicalDiagnosisService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param kgMedicalDiagnosis 病历样本诊断表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalDiagnosis_view')" )
    public R getKgMedicalDiagnosisPage(@ParameterObject Page page, @ParameterObject KgMedicalDiagnosis kgMedicalDiagnosis) {
        LambdaQueryWrapper<KgMedicalDiagnosis> wrapper = Wrappers.lambdaQuery();
        return R.ok(kgMedicalDiagnosisService.page(page, wrapper));
    }


    /**
     * 通过id查询病历样本诊断表
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{id}" )
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalDiagnosis_view')" )
    public R getById(@PathVariable("id" ) String id) {
        return R.ok(kgMedicalDiagnosisService.getById(id));
    }

    /**
     * 新增病历样本诊断表
     * @param kgMedicalDiagnosis 病历样本诊断表
     * @return R
     */
    @Operation(summary = "新增病历样本诊断表" , description = "新增病历样本诊断表" )
    @SysLog("新增病历样本诊断表" )
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalDiagnosis_add')" )
    public R save(@RequestBody KgMedicalDiagnosis kgMedicalDiagnosis) {
        return R.ok(kgMedicalDiagnosisService.save(kgMedicalDiagnosis));
    }

    /**
     * 修改病历样本诊断表
     * @param kgMedicalDiagnosis 病历样本诊断表
     * @return R
     */
    @Operation(summary = "修改病历样本诊断表" , description = "修改病历样本诊断表" )
    @SysLog("修改病历样本诊断表" )
    @PutMapping
   // @PreAuthorize("@pms.hasPermission('admin_kgMedicalDiagnosis_edit')" )
    public R updateById(@RequestBody KgMedicalDiagnosis kgMedicalDiagnosis) {
        return R.ok(kgMedicalDiagnosisService.updateById(kgMedicalDiagnosis));
    }

    /**
     * 通过id删除病历样本诊断表
     * @param ids id列表
     * @return R
     */
    @Operation(summary = "通过id删除病历样本诊断表" , description = "通过id删除病历样本诊断表" )
    @SysLog("通过id删除病历样本诊断表" )
    @DeleteMapping
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalDiagnosis_del')" )
    public R removeById(@RequestBody String[] ids) {
        return R.ok(kgMedicalDiagnosisService.removeBatchByIds(CollUtil.toList(ids)));
    }


    /**
     * 导出excel 表格
     * @param kgMedicalDiagnosis 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
	 * @ignore
     */
    @ResponseExcel
    @GetMapping("/export")
   //@PreAuthorize("@pms.hasPermission('admin_kgMedicalDiagnosis_export')" )
    public List<KgMedicalDiagnosis> export(KgMedicalDiagnosis kgMedicalDiagnosis,String[] ids) {
        return kgMedicalDiagnosisService.list(Wrappers.lambdaQuery(kgMedicalDiagnosis).in(ArrayUtil.isNotEmpty(ids), KgMedicalDiagnosis::getId, ids));
    }
}