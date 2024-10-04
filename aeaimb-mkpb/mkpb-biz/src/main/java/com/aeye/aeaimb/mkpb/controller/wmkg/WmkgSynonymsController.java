package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.common.security.annotation.Inner;
import com.aeye.aeaimb.mkpb.dto.cdn.WmkgSynonymsDto;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgSynonymsService;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgSynonymsVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 同义词
 *
 * @author linkeke
 * @since 2024-08-05 14:07:38
 */
@RestController
@RequestMapping("synonyms")
public class WmkgSynonymsController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgSynonymsService wmkgSynonymsService;

	/**
	 * 获取同义词
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@Inner(value = false)
	@PostMapping("/getSynonyms")
	public List<WmkgSynonymsVO> getSynonyms(@RequestBody WmkgSynonymsDto dto){
		return wmkgSynonymsService.getSynonyms(dto.getSourceName(), dto.getObjType());
	}

}

