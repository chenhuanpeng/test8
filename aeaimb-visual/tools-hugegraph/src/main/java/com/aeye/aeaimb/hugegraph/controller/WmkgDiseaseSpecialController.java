package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseSpecialService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 疾病特殊处理建议(WmkgDiseaseSpecial)表控制层
 *
 * @author linkeke
 * @since 2024-08-27 10:43:30
 */
@RestController
@RequestMapping("wmkgDiseaseSpecial")
public class WmkgDiseaseSpecialController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDiseaseSpecialService wmkgDiseaseSpecialService;



}

