package com.aeye.aeaimb.mkpb.controller.kg;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.mkpb.service.kg.KgMedicalDiagnosisService;
import com.aeye.aeaimb.mkpb.service.kg.KgMedicalExampleService;
import com.aeye.cdss.admin.api.dto.KgMedicalExampleDTO;
import com.aeye.cdss.admin.api.entity.BaseDiseaseDict;
import com.aeye.cdss.admin.api.entity.KgMedicalDiagnosis;
import com.aeye.cdss.admin.api.vo.CaseSampleVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.cdss.admin.api.entity.KgMedicalExample;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 病历样本表
 *
 * @author cdss
 * @date 2023-12-19 15:39:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/kgMedicalExample" )
@Tag(description = "kgMedicalExample" , name = "病历样本表管理" )
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class KgMedicalExampleController {

    private final KgMedicalExampleService kgMedicalExampleService;

	private final KgMedicalDiagnosisService kgMedicalDiagnosisService;

//	private final BaseDiseaseDictService baseDiseaseDictService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseSampleVO 病历样本表
     * @return
     */
    @Operation(summary = "分页查询" , description = "分页查询" )
    @GetMapping("/page" )
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalExample_view')" )
    public R<IPage<KgMedicalExampleDTO>> getKgMedicalExamplePage(@ParameterObject Page page, @ParameterObject CaseSampleVO caseSampleVO) {
		IPage<KgMedicalExampleDTO> pp = kgMedicalExampleService.getUniversalPaging(page,caseSampleVO);

		for (KgMedicalExampleDTO kme:pp.getRecords()){
			if (BeanUtil.isEmpty(kme)){
				return R.ok();
			}
			LambdaQueryWrapper<KgMedicalDiagnosis> lqw = new LambdaQueryWrapper<>();
			lqw.eq(KgMedicalDiagnosis::getMedicalId,kme.getMedicalId());
			List<KgMedicalDiagnosis> list = kgMedicalDiagnosisService.list(lqw);
			// 使用流来映射每个对象的关键词，并收集为一个字符串
			String resultId = list.stream()               // 转换成流
					.map(KgMedicalDiagnosis::getSickCode)  // 映射每个对象的getKey()
					.collect(Collectors.joining(",")); // 用逗号连接成一个字符串

			String resultName = list.stream()               // 转换成流
					.map(KgMedicalDiagnosis::getSickName)  // 映射每个对象的getKey()
					.collect(Collectors.joining(",")); // 用逗号连接成一个字符串
			kme.setSickId(resultId);
			kme.setSickName(resultName);
			kme.setSickNames(list.stream()
					.map(KgMedicalDiagnosis::getSickName)
					.collect(Collectors.toList()));
			kme.setSickIds(list.stream()
					.map(KgMedicalDiagnosis::getSickCode)
					.collect(Collectors.toList()));
		}
        return R.ok(pp);
    }


    /**
     * 通过id查询病历样本表
     * @param medicalId id
     * @return R
     */
    @Operation(summary = "通过id查询" , description = "通过id查询" )
    @GetMapping("/{medicalId}" )
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalExample_view')" )
    public R<KgMedicalExampleDTO> getById(@PathVariable("medicalId" ) String medicalId) {
		KgMedicalExample kme = kgMedicalExampleService.getById(medicalId);
		if (BeanUtil.isEmpty(kme)){
			return R.ok();
		}
		KgMedicalExampleDTO dto = new KgMedicalExampleDTO();
		BeanUtil.copyProperties(kme,dto);
		LambdaQueryWrapper<KgMedicalDiagnosis> lqw = new LambdaQueryWrapper<>();
		lqw.eq(KgMedicalDiagnosis::getMedicalId,medicalId);
		List<KgMedicalDiagnosis> list = kgMedicalDiagnosisService.list(lqw);
		// 使用流来映射每个对象的关键词，并收集为一个字符串
		String resultId = list.stream()               // 转换成流
				.map(KgMedicalDiagnosis::getSickCode)  // 映射每个对象的getKey()
				.collect(Collectors.joining(",")); // 用逗号连接成一个字符串

		String resultName = list.stream()               // 转换成流
				.map(KgMedicalDiagnosis::getSickName)  // 映射每个对象的getKey()
				.collect(Collectors.joining(",")); // 用逗号连接成一个字符串
		dto.setSickId(resultId);
		dto.setSickName(resultName);
		dto.setSickNames(list.stream()
				.map(KgMedicalDiagnosis::getSickName)
				.collect(Collectors.toList()));
		dto.setSickIds(list.stream()
				.map(KgMedicalDiagnosis::getSickCode)
				.collect(Collectors.toList()));
		return R.ok(dto);
    }

    /**
     * 新增病历样本表
     * @param kgMedicalExampleDto 病历样本表
     * @return R
     */
//    @Operation(summary = "新增病历样本表" , description = "新增病历样本表" )
//    @SysLog("新增病历样本表" )
//    @PostMapping("/save" )
//    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalExample_add')" )
//    public R save(@RequestBody KgMedicalExampleDTO kgMedicalExampleDto) {
//		KgMedicalExample kme = new KgMedicalExample();
//		BeanUtil.copyProperties(kgMedicalExampleDto,kme);
//		kgMedicalExampleService.save(kme);
//		if (StringUtils.isNotBlank(kgMedicalExampleDto.getSickId())){
//			String[] st =kgMedicalExampleDto.getSickId().split(",");
//			for (int i =0;i<st.length;i++){
//				String diseaseCode = st[i];
//				BaseDiseaseDict diseaseDict = baseDiseaseDictService.getById(diseaseCode);
//				KgMedicalDiagnosis kmd = new KgMedicalDiagnosis();
//				kmd.setMedicalId(kme.getMedicalId());
//				kmd.setSickName(diseaseDict.getDiseaseName());
//				kmd.setSickCode(diseaseDict.getDiseaseCode());
//				kmd.setSickIndex(i);
//				kgMedicalDiagnosisService.save(kmd);
//			}
//		}
//        return R.ok();
//    }

    /**
     * 修改病历样本表
     * @param kgMedicalExampleDto 病历样本表
     * @return R
     */
//    @Operation(summary = "修改病历样本表" , description = "修改病历样本表" )
//    @SysLog("修改病历样本表" )
//    @PutMapping("/updateById")
   // @PreAuthorize("@pms.hasPermission('admin_kgMedicalExample_edit')" )
//    public R updateById(@RequestBody KgMedicalExampleDTO kgMedicalExampleDto) {
//		KgMedicalExample kme = new KgMedicalExample();
//		BeanUtil.copyProperties(kgMedicalExampleDto,kme);
//		kgMedicalExampleService.updateById(kme);
//		LambdaQueryWrapper<KgMedicalDiagnosis> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//		lambdaQueryWrapper.eq(KgMedicalDiagnosis::getMedicalId,kgMedicalExampleDto.getMedicalId());
//		kgMedicalDiagnosisService.remove(lambdaQueryWrapper);
//		if (StringUtils.isNotBlank(kgMedicalExampleDto.getSickId())){
//			String[] st =kgMedicalExampleDto.getSickId().split(",");
//			for (int i =0;i<st.length;i++){
//				String diseaseCode = st[i];
//				BaseDiseaseDict diseaseDict = baseDiseaseDictService.getById(diseaseCode);
//				KgMedicalDiagnosis kmd = new KgMedicalDiagnosis();
//				kmd.setMedicalId(kme.getMedicalId());
//				kmd.setSickName(diseaseDict.getDiseaseName());
//				kmd.setSickCode(diseaseDict.getDiseaseCode());
//				kmd.setSickIndex(i);
//				kgMedicalDiagnosisService.save(kmd);
//			}
//		}
//        return R.ok();
//    }

    /**
     * 通过id删除病历样本表
     * @param id medicalId列表
     * @return R
     */
    @Operation(summary = "通过id删除病历样本表" , description = "通过id删除病历样本表" )
    @SysLog("通过id删除病历样本表" )
    @DeleteMapping("/removeById")
    //@PreAuthorize("@pms.hasPermission('admin_kgMedicalExample_del')" )
    public R removeById(String id) {
		LambdaQueryWrapper<KgMedicalDiagnosis> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(KgMedicalDiagnosis::getMedicalId,id);
		kgMedicalDiagnosisService.remove(lambdaQueryWrapper);
        return R.ok(kgMedicalExampleService.removeBatchByIds(CollUtil.toList(id)));
    }


    /**
     * 导出excel 表格
     * @param kgMedicalExample 查询条件
   	 * @param ids 导出指定ID
     * @return excel 文件流
	 * @ignore
     */
    @ResponseExcel
    @GetMapping("/export")
   //@PreAuthorize("@pms.hasPermission('admin_kgMedicalExample_export')" )
    public List<KgMedicalExample> export(KgMedicalExample kgMedicalExample,String[] ids) {
        return kgMedicalExampleService.list(Wrappers.lambdaQuery(kgMedicalExample).in(ArrayUtil.isNotEmpty(ids), KgMedicalExample::getMedicalId, ids));
    }
}
