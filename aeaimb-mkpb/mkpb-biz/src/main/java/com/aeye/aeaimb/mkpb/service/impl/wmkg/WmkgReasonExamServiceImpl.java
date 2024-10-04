package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonExamMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExam;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExamItem;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonExamItemService;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonExamService;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 推理检查(WmkgReasonExam)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
@Service("wmkgReasonExamService")
@DS("wmkg")
public class WmkgReasonExamServiceImpl extends ServiceImpl<WmkgReasonExamMapper, WmkgReasonExam> implements WmkgReasonExamService {

	@Resource
	private WmkgReasonExamItemService wmkgReasonExamItemService;

	@Override
	public List<WmkgReasonExam> getReasonExam(String name) {
		return this.list(Wrappers.<WmkgReasonExam>lambdaQuery().like(StringUtils.isNotBlank(name),WmkgReasonExam::getExamName, name));
	}

	@Override
	public List<WmkgReasonExamItem> getReasonExamItem(String name) {
		return wmkgReasonExamItemService.list(Wrappers.<WmkgReasonExamItem>lambdaQuery()
				.eq(WmkgReasonExamItem::getExamName, name));
	}
}
