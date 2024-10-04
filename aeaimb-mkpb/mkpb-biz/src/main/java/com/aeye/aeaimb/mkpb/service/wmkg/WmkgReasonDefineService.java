package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefinePageDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDefine;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineCountVO;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 推理疾病定义(WmkgReasonDefine)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
public interface WmkgReasonDefineService extends IService<WmkgReasonDefine> {

	IPage<ReasonDefineCountVO> getReasonDefineCountPageList(Page<WmkgReasonDefine> page, ReasonDefinePageDTO dto);
	IPage<ReasonDefineVO> getReasonDefinePageList(Page<WmkgReasonDefine> page, ReasonDefinePageDTO dto);

	void setReasonDefineStatus(ReasonDefineDTO dto);
}
