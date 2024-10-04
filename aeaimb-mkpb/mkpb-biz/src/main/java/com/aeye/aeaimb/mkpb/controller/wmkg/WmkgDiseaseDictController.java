package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.dto.wmkg.WmkgDiseaseDictDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDiseaseDict;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDiseaseDictService;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgDiseaseDictVO;
import com.aeye.cdss.admin.api.dto.IdListDTO;
import com.aeye.cdss.admin.api.dto.dict.BaseDiseaseDictPageDTO;
import com.aeye.cdss.admin.api.dto.dict.QueryBaseDiseaseDictDTO;
import com.aeye.cdss.admin.api.vo.dict.BaseDiseaseDictVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 疾病字典
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@RestController
@RequestMapping("wmkgDiseaseDict")
public class WmkgDiseaseDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDiseaseDictService wmkgDiseaseDictService;

	/**
	 * 添加或更新疾病字典
	 * @param baseDiseaseDictDTO 疾病字典
	 * @return false true
	 */
	@SysLog("添加或更新疾病字典")
	@PostMapping("saveOrUpdate")
	public R<Boolean> save(@Valid @RequestBody WmkgDiseaseDictDTO baseDiseaseDictDTO) {
		WmkgDiseaseDict baseDiseaseDict = BeanConvertUtil.convertBean(baseDiseaseDictDTO, WmkgDiseaseDict.class);
		wmkgDiseaseDictService.saveOrUpdateDiseaseDict(baseDiseaseDict);
		return R.ok();
	}

	/**
	 * 疾病字典分页列表
	 */
	@PostMapping("findBaseDiseaseDictPageList")
	public R<IPage<WmkgDiseaseDictVO>> findBaseDiseaseDictPageList(@RequestBody @Validated BaseDiseaseDictPageDTO dto) {
		Page<WmkgDiseaseDict> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<WmkgDiseaseDictVO> baseDiseaseDictVOIPage = wmkgDiseaseDictService.findBaseDiseaseDictPageList(page, dto);
		return R.ok(baseDiseaseDictVOIPage);
	}

	/**
	 * 删除疾病字典（支持批量）
	 */
	@SysLog("删除疾病字典")
	@PostMapping("deleteByIds")
	public R<Boolean> deleteByIds(@RequestBody IdListDTO dto) {
		wmkgDiseaseDictService.removeDiseaseDict(dto.getIds());
		return R.ok();
	}

	/**
	 * 获取疾病字典列表
	 */
	@PostMapping("findBaseDiseaseDictList")
	public R<List<WmkgDiseaseDictVO>> findBaseDiseaseDictList(@RequestBody @Validated QueryBaseDiseaseDictDTO dto) {
		return R.ok(wmkgDiseaseDictService.findBaseDiseaseDictList(dto));
	}

}

