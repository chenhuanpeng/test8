package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonOp;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonOpMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonOpService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 推理手术(WmkgReasonOp)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:58:51
 */
@Service("wmkgReasonOpService")
public class WmkgReasonOpServiceImpl extends ServiceImpl<WmkgReasonOpMapper, WmkgReasonOp> implements WmkgReasonOpService {
	@Resource
	private WmkgReasonOpMapper wmkgReasonOpMapper;


}
