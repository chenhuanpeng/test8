package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonSymptom;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonSymptomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理症状
 *
 * @author linkeke
 * @since 2024-08-06 16:45:44
 */
@RestController
@RequestMapping("reasonSymptom")
public class WmkgReasonSymptomController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgReasonSymptomService wmkgReasonSymptomService;

	/**
	 * 查询症状
	 * @param name 症状名称
	 * @return 症状列表
	 */
	@GetMapping("/getReasonSymptom")
	public R<List<WmkgReasonSymptom>> getReasonSymptom(@RequestParam("name") String name){
		return R.ok(wmkgReasonSymptomService.getReasonSymptom(name));
	}

}

