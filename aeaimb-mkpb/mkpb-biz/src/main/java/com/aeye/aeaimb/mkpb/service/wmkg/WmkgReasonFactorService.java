package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonFactor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 推理因子分类(WmkgReasonFactor)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
public interface WmkgReasonFactorService extends IService<WmkgReasonFactor> {

	List<WmkgReasonFactor> getReasonFactorList(String name);
}
