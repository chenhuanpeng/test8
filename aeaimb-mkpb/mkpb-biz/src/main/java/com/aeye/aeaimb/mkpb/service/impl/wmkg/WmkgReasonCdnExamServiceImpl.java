package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnExamMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonCdnExam;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonCdnExamService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 推理规则条件检查(WmkgReasonCdnExam)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Service("wmkgReasonCdnExamService")
@DS("wmkg")
public class WmkgReasonCdnExamServiceImpl extends ServiceImpl<WmkgReasonCdnExamMapper, WmkgReasonCdnExam> implements WmkgReasonCdnExamService {
	@Resource
	private WmkgReasonCdnExamMapper wmkgReasonCdnExamMapper;


}
