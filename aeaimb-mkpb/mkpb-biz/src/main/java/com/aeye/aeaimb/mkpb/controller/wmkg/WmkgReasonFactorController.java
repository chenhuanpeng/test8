package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonFactor;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonFactorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理因子分类
 *
 * @author linkeke
 * @since 2024-08-06 15:37:16
 */
@RestController
@RequestMapping("reasonFactor")
public class WmkgReasonFactorController {
    /**
     * 服务对象
     */
    @Resource
    private WmkgReasonFactorService wmkgReasonFactorService;

	/**
	 * 获取推理因子列表
	 * @param name 父级推理因子
	 * @return 推理因子列表
	 */
	@GetMapping("/getReasonFactorList")
	public R<List<WmkgReasonFactor>> getReasonFactorList(@RequestParam("name") String name){
		return R.ok(wmkgReasonFactorService.getReasonFactorList(name));
	}

}

