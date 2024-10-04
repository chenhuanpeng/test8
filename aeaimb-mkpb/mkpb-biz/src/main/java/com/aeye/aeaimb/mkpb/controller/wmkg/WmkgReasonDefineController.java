package com.aeye.aeaimb.mkpb.controller.wmkg;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefinePageDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDefine;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonDefineService;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineCountVO;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineVO;
import com.aeye.aeaimb.wmkg.dto.SuspectedDisease;
import com.aeye.aeaimb.wmkg.dto.wm.MedicalStructFull;
import com.aeye.aeaimb.wmkg.feign.WesternCdssService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 推理疾病方案定义
 *
 * @author linkeke
 * @since 2024-08-05 14:50:04
 */
@RestController
@RequestMapping("reasonDefine")
public class WmkgReasonDefineController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgReasonDefineService wmkgReasonDefineService;

	@Resource
	private WesternCdssService cdssAbilityService;

	/**
	 * 获取疾病的方案统计分页列表
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@PostMapping("/getReasonDefineCountPageList")
	public R<IPage<ReasonDefineCountVO>> getReasonDefineCountPageList(@RequestBody ReasonDefinePageDTO dto){
		Page<WmkgReasonDefine> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<ReasonDefineCountVO> reasonDefinePageList = wmkgReasonDefineService.getReasonDefineCountPageList(page, dto);
		return R.ok(reasonDefinePageList);
	}

	/**
	 * 方案分页列表
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@PostMapping("/getReasonDefinePageList")
	public R<IPage<ReasonDefineVO>> getReasonDefinePageList(@RequestBody @Validated ReasonDefinePageDTO dto){
		if (StrUtil.isBlank(dto.getDiseaseCode())){
			return R.failed("疾病编码不能为空");
		}
		Page<WmkgReasonDefine> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<ReasonDefineVO> reasonDefineVOIPage = wmkgReasonDefineService.getReasonDefinePageList(page, dto);
		return R.ok(reasonDefineVOIPage);
	}

	/**
	 * 设置方案状态 , 上线、下线、删除
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@PostMapping("/setReasonDefineStatus")
	public R<Boolean> setReasonDefineStatus(@RequestBody @Validated ReasonDefineDTO dto){
		if (StrUtil.isBlank(dto.getReasonId())){
			return R.failed("推理ID不能为空");
		}

		if (StrUtil.isBlank(dto.getDelFlag()) && Objects.isNull(dto.getReasonStatus())){
			return R.failed("状态参数不能同时为空");
		}

		wmkgReasonDefineService.setReasonDefineStatus(dto);
		return R.ok();
	}

	/**
	 * 基于规则的预测
	 * @param medRecord
	 * @return
	 */
	@PostMapping("predict")
	public R<List<SuspectedDisease>>  predictByRules(@RequestBody MedicalStructFull medRecord){
		return cdssAbilityService.predictByRules(medRecord);
	}

	/**
	 * 预测方案得分
	 * @param medRecord
	 * @return
	 */
	@PostMapping("predict-reason/{reasonId}")
	public R<SuspectedDisease>  predictByReason(@PathVariable("reasonId") String reasonId, @RequestBody MedicalStructFull medRecord){
		return cdssAbilityService.predictByReason(reasonId,medRecord);
	}
}

