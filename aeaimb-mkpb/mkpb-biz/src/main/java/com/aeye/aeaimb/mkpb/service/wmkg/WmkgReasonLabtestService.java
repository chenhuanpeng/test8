package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtest;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonLabtestItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 推理检验(WmkgReasonLabtest)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:41:25
 */
public interface WmkgReasonLabtestService extends IService<WmkgReasonLabtest> {

	List<WmkgReasonLabtest> getReasonLabtest(String name);
	List<WmkgReasonLabtestItem> getReasonLabtestItem(String name);
}
