package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonExam;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonExamMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonExamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理检查(WmkgReasonExam)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:52
 */
@Service("wmkgReasonExamService")
public class WmkgReasonExamServiceImpl extends ServiceImpl<WmkgReasonExamMapper, WmkgReasonExam> implements WmkgReasonExamService {
	@Resource
	private WmkgReasonExamMapper wmkgReasonExamMapper;


}
