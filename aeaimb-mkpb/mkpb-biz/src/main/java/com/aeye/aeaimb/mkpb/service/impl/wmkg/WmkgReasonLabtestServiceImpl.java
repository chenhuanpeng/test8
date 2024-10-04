package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonLabtestMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtest;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtestItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonLabtestItemService;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonLabtestService;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理检验(WmkgReasonLabtest)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
@Service("wmkgReasonLabtestService")
@DS("wmkg")
public class WmkgReasonLabtestServiceImpl extends ServiceImpl<WmkgReasonLabtestMapper, WmkgReasonLabtest> implements WmkgReasonLabtestService {

	@Resource
	private WmkgReasonLabtestItemService wmkgReasonLabtestItemService;



	@Override
	public List<WmkgReasonLabtest> getReasonLabtest(String name) {
		return this.list(Wrappers.<WmkgReasonLabtest>lambdaQuery().like(StringUtils.isNotBlank(name),WmkgReasonLabtest::getLabtestName, name));
	}

	@Override
	public List<WmkgReasonLabtestItem> getReasonLabtestItem(String name) {
		return wmkgReasonLabtestItemService.list(Wrappers.<WmkgReasonLabtestItem>lambdaQuery()
				.eq(WmkgReasonLabtestItem::getLabtestName, name));
	}
}
