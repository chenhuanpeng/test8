package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExam;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonExamItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 推理检查(WmkgReasonExam)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
public interface WmkgReasonExamService extends IService<WmkgReasonExam> {

	List<WmkgReasonExam> getReasonExam(String name);
	List<WmkgReasonExamItem> getReasonExamItem(String name);
}
