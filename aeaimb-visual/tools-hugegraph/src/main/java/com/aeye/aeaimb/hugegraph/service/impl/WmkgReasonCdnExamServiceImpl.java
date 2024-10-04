package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonCdnExam;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonCdnExamMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonCdnExamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理规则条件检查(WmkgReasonCdnExam)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:55
 */
@Service("wmkgReasonCdnExamService")
public class WmkgReasonCdnExamServiceImpl extends ServiceImpl<WmkgReasonCdnExamMapper, WmkgReasonCdnExam> implements WmkgReasonCdnExamService {
	@Resource
	private WmkgReasonCdnExamMapper wmkgReasonCdnExamMapper;


}
