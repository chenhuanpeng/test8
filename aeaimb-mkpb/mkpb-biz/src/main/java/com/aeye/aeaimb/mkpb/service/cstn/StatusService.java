package com.aeye.aeaimb.mkpb.service.cstn;

import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnOperationHistory;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPathVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chenhuanpeng
 * @since 2024-08-30 15:23:08
 */
public interface StatusService  extends IService<CstnPath>{

	/**
	 * 更新状态  写历史记录
	 * @param operation
	 * @param status
	 * @return
	 */
	Boolean updateStatus(String status, CstnOperationHistory operation );
}
