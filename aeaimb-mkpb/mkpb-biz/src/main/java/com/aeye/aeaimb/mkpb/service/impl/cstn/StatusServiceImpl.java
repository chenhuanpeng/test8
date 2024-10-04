package com.aeye.aeaimb.mkpb.service.impl.cstn;


import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnOperationHistory;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.mapper.cstn.CstnPathMapper;
import com.aeye.aeaimb.mkpb.service.cstn.CstnOperationHistoryService;
import com.aeye.aeaimb.mkpb.service.cstn.CstnPathService;
import com.aeye.aeaimb.mkpb.service.cstn.StatusService;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPathVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 状态 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Service
@DS("wmkg")
public class StatusServiceImpl extends ServiceImpl<CstnPathMapper, CstnPath>  implements StatusService {

	@Resource
	private CstnPathMapper mapper;

	@Resource
	private CstnOperationHistoryService operationService;


	@Override
	@Transactional
	public Boolean updateStatus( String status,CstnOperationHistory operation ) {

		operationService.save(operation);

		CstnPath path = this.getById(operation.getRefId());
		path.setPathStatus(status);
		return this.updateById(path);
	}

}
