package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExam;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExamItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonExamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理检查
 *
 * @author linkeke
 * @since 2024-08-06 15:37:16
 */
@RestController
@RequestMapping("reasonExam")
public class WmkgReasonExamController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgReasonExamService wmkgReasonExamService;

	/**
	 * 获取检查信息
	 * @param name 检查名称
	 * @return 检查列表
	 */
	@GetMapping("/getReasonExam")
	public R<List<WmkgReasonExam>> getReasonExam(@RequestParam("name") String name){
		return R.ok(wmkgReasonExamService.getReasonExam(name));
	}

	/**
	 * 获取检查项目的子项
	 * @param name 检查名称
	 * @return 列表
	 */
	@GetMapping("/getReasonExamItem")
	public R<List<WmkgReasonExamItem>> getReasonExamItem(@RequestParam("name") String name){
		return R.ok(wmkgReasonExamService.getReasonExamItem(name));
	}

}

