package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgMedicalTermCategoryService;
import com.aeye.cdss.admin.api.dto.dict.BaseMedicalTermCategoryDictDTO;
import com.aeye.cdss.admin.api.vo.dict.BaseMedicalTermCategoryVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 医疗术语章节分类 （疾病和手术分类）
 *
 * @author linkeke
 * @since 2024-08-24 10:52:55
 */
@RestController
@RequestMapping("medicalTermCategory")
public class WmkgMedicalTermCategoryController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgMedicalTermCategoryService wmkgMedicalTermCategoryService;

	/**
	 * 获取类目信息
	 * @param dto 入参
	 * @return 类目信息列表
	 */
	@PostMapping("findBaseMedicalTermCategoryList")
	public R<List<BaseMedicalTermCategoryVO>> findBaseMedicalTermCategoryList(@RequestBody @Validated BaseMedicalTermCategoryDictDTO dto) {
		List<BaseMedicalTermCategoryVO> baseMedicalTermCategoryList = wmkgMedicalTermCategoryService.findBaseMedicalTermCategoryList(dto);
		return R.ok(baseMedicalTermCategoryList);
	}

	/**
	 * 获取疾病分类版本列表
	 * @return 版本列表
	 */
	@GetMapping("/listNorType")
	public R<List<Object>> listNorType() {
		List<Object> objects = wmkgMedicalTermCategoryService.listNorType();
		return R.ok(objects);
	}
}

