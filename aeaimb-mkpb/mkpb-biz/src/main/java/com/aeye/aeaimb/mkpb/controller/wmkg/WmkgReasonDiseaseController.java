package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDisease;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonDiseaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理疾病
 *
 * @author linkeke
 * @since 2024-08-06 15:37:15
 */
@RestController
@RequestMapping("reasonDisease")
public class WmkgReasonDiseaseController {
    /**
     * 服务对象
     */
    @Resource
    private WmkgReasonDiseaseService wmkgReasonDiseaseService;

	/**
	 * 推理疾病
	 * @param name 疾病名称
	 * @return 疾病列表
	 */
	@GetMapping("/getReasonDisease")
	public R<List<WmkgReasonDisease>> getReasonDisease(@RequestParam("name") String name){
		return R.ok(wmkgReasonDiseaseService.getReasonDisease(name));
	}

}

