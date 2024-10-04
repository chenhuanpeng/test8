package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonOpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 推理手术
 *
 * @author linkeke
 * @since 2024-08-06 15:37:16
 */
@RestController
@RequestMapping("reasonOp")
public class WmkgReasonOpController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgReasonOpService wmkgReasonOpService;



}

