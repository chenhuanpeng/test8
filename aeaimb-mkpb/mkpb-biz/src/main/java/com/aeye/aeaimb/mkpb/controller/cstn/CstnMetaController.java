package com.aeye.aeaimb.mkpb.controller.cstn;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.service.cstn.CstnMetaService;
import com.aeye.aeaimb.mkpb.vo.cstn.CommonVO;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * [问诊]问诊条件元数据 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/cstn-meta")
public class CstnMetaController {

	@Autowired
	private CstnMetaService metaService;

	@GetMapping(value = "/queryMetaDataList")
	public R<List<Tree<String>>> queryMetaDataList(@RequestParam(required = false) String bitFlag) {
		List<Tree<String>> trees = metaService.queryMetaDataList(bitFlag);
		return R.ok(trees);
	}


	@GetMapping(value = "/queryCommonList")
	public R<List<CommonVO>> queryCommonList(@RequestParam(required = false) String keyword,
											 @RequestParam(required = false) String id) {

		List<CommonVO> commonVOS = metaService.queryCommonList(keyword,id);
		return R.ok(commonVOS);
	}


}
