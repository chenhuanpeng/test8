package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.dto.cdn.AuReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.exam.AddReasonCdnExamDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.multi.AddReasonCdnFrequentDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.symptom.AddReasonCdnSymptomDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.test.AddReasonCdnLabtestDTO;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 推理规则条件
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@RestController
@RequestMapping("reasonCdn")
public class WmkgReasonCdnController {
    /**
     * 服务对象
     */
    @Resource
    private WmkgReasonCdnService wmkgReasonCdnService;

	/**
	 * 保存症状推理规则条件
	 * @param dto 输入类
	 * @return 成功或失败
	 */
	@PostMapping("/saveReasonCdnSymp")
	public R<String> saveReasonCdnSymp(@RequestBody AuReasonDefineDTO<AddReasonCdnSymptomDTO> dto){
		return R.ok(wmkgReasonCdnService.saveReasonCdnSymp(dto));
	}

	/**
	 * 获取症状推理规则条件
	 * @param reasonId 理论id
	 * @return 输出对象
	 */
	@GetMapping("/getReasonCdnSymp")
	public R<AuReasonDefineDTO<AddReasonCdnSymptomDTO>> getReasonCdnSymp(@RequestParam("reasonId") String reasonId){
		return R.ok(wmkgReasonCdnService.getReasonCdnSymp(reasonId));
	}


	/**
	 * 保存多发因素推理规则条件
	 * @param dto 输入对象
	 * @return 成功或失败
	 */
	@PostMapping("/saveReasonCdnMulti")
	public R<String> saveReasonCdnMulti(@RequestBody AuReasonDefineDTO<AddReasonCdnFrequentDTO> dto){
		return R.ok(wmkgReasonCdnService.saveReasonCdnMulti(dto));
	}

	/**
	 * 获取多发因素推理规则条件
	 * @param reasonId 理论id
	 * @return 获取多因子推理规则条件
	 */
	@GetMapping("/getReasonCdnMulti")
	public R<AuReasonDefineDTO<AddReasonCdnFrequentDTO>> getReasonCdnMulti(@RequestParam("reasonId") String reasonId){
		return R.ok(wmkgReasonCdnService.getReasonCdnMulti(reasonId,"多发因素"));
	}

	/**
	 * 保存体格检查推理规则条件
	 * @param dto 输入对象
	 * @return 成功或失败
	 */
	@PostMapping("/saveReasonCdnPhysical")
	public R<String> saveReasonCdnPhysical(@RequestBody AuReasonDefineDTO<AddReasonCdnFrequentDTO> dto){
		return R.ok(wmkgReasonCdnService.saveReasonCdnPhysical(dto));
	}

	/**
	 *  获取体格检查推理规则条件
	 * @param reasonId 方案id
	 * @return 输出对象
	 */
	@GetMapping("/getReasonCdnPhysical")
	public R<AuReasonDefineDTO<AddReasonCdnFrequentDTO>> getReasonCdnPhysical(@RequestParam("reasonId") String reasonId){
		return R.ok(wmkgReasonCdnService.getReasonCdnPhysical(reasonId,"体格检查"));
	}

	/**
	 * 保存检查项目推理规则条件
	 * @param dto 输入对象
	 * @return 输出对象
	 */
	@PostMapping("/saveReasonCdnExam")
	public R<String> saveReasonCdnExam(@RequestBody AuReasonDefineDTO<AddReasonCdnExamDTO> dto){
		return R.ok(wmkgReasonCdnService.saveReasonCdnExam(dto));
	}

	/**
	 * 获取检查项目推理规则条件
	 * @param reasonId 方案id
	 * @return 规则列表
	 */
	@GetMapping("/getReasonCdnExam")
	public R<AuReasonDefineDTO<AddReasonCdnExamDTO>> getReasonCdnExam(@RequestParam("reasonId") String reasonId){
		return R.ok(wmkgReasonCdnService.getReasonCdnExam(reasonId,"检查项目"));
	}

	/**
	 * 保存检验项目推理规则条件
	 * @param dto 输入对象
	 * @return 输出对象
	 */
	@PostMapping("/saveReasonCdnLabtest")
	public R<String> saveReasonCdnLabtest(@RequestBody AuReasonDefineDTO<AddReasonCdnLabtestDTO> dto){
		return R.ok(wmkgReasonCdnService.saveReasonCdnLabtest(dto));
	}

	/**
	 * 获取检验项目推理规则条件
	 * @param reasonId 方案id
	 * @return 规则列表
	 */
	@GetMapping("/getReasonCdnLabtest")
	public R<AuReasonDefineDTO<AddReasonCdnLabtestDTO>> getReasonCdnLabtest(@RequestParam("reasonId") String reasonId){
		return R.ok(wmkgReasonCdnService.getReasonCdnLabtest(reasonId,"检验项目"));
	}

}

