package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtest;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtestItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonLabtestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理检验
 *
 * @author linkeke
 * @since 2024-08-06 15:37:16
 */
@RestController
@RequestMapping("reasonLabtest")
public class WmkgReasonLabtestController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgReasonLabtestService wmkgReasonLabtestService;

	/**
	 * 检验数据
	 * @param name 检验数据
	 * @return 检验数据列表
	 */
	@GetMapping("/getReasonLabtest")
	public R<List<WmkgReasonLabtest>> getReasonLabtest(@RequestParam("name") String name){
		return R.ok(wmkgReasonLabtestService.getReasonLabtest(name));
	}

	/**
	 * 检验项目子项
	 * @param name 检验项目
	 * @return 列表
	 */
	@GetMapping("/getReasonLabtestItem")
	public R<List<WmkgReasonLabtestItem>> getReasonLabtestItem(@RequestParam("name") String name){
		return R.ok(wmkgReasonLabtestService.getReasonLabtestItem(name));
	}

}

