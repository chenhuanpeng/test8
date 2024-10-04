package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.dto.wmkg.WmkgSymptomDictDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgSymptomDict;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgSymptomDictService;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgSymptomDictVO;
import com.aeye.cdss.admin.api.dto.IdListDTO;
import com.aeye.cdss.admin.api.dto.dict.BaseSymptomDictPageDTO;
import com.aeye.cdss.admin.api.dto.dict.BaseSymptomDictQueryDTO;
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
 * 症状字典
 *
 * @author linkeke
 * @since 2024-08-24 13:38:21
 */
@RestController
@RequestMapping("wmkgSymptomDict")
public class WmkgSymptomDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgSymptomDictService wmkgSymptomDictService;

	/**
	 * 添加或更新症状字典
	 * @param wmkgSymptomDictDTO 症状字典
	 * @return false true
	 */
	@SysLog("添加或更新症状字典")
	@PostMapping("saveOrUpdate")
	public R<Boolean> save(@Valid @RequestBody WmkgSymptomDictDTO wmkgSymptomDictDTO) {
		WmkgSymptomDict baseSymptomDict = BeanConvertUtil.convertBean(wmkgSymptomDictDTO, WmkgSymptomDict.class);
		wmkgSymptomDictService.saveOrUpdateSymptomDict(baseSymptomDict,wmkgSymptomDictDTO.getAddFlag());
		return R.ok();
	}

	/**
	 * 症状字典分页列表
	 */
	@PostMapping("findBaseSymptomDictPageList")
	public R<IPage<WmkgSymptomDictVO>> findBaseSymptomDictPageList(@RequestBody @Validated BaseSymptomDictPageDTO dto) {
		Page<WmkgSymptomDict> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<WmkgSymptomDictVO> baseDiseaseDictVOIPage = wmkgSymptomDictService.findBaseSymptomDictPageList(page, dto);
		return R.ok(baseDiseaseDictVOIPage);
	}

	/**
	 * 获取症状字典列表
	 * @param dto 症状字典列表查询入参
	 * @return 列表
	 */
	@PostMapping("findBaseSymptomDictList")
	public R<List<WmkgSymptomDictVO>> findBaseSymptomDictList(@RequestBody @Validated BaseSymptomDictQueryDTO dto) {
		return R.ok(wmkgSymptomDictService.findBaseSymptomDictList(dto));
	}

	/**
	 * 删除症状字典（支持批量）
	 */
	@SysLog("删除症状字典")
	@PostMapping("deleteByIds")
	public R<Boolean> deleteByIds(@RequestBody IdListDTO dto) {
		wmkgSymptomDictService.removeSymptomDict(dto.getIds());
		return R.ok();
	}

}

