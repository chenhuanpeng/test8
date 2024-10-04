package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonSymptom;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 推理症状(WmkgReasonSymptom)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:44:51
 */
public interface WmkgReasonSymptomService extends IService<WmkgReasonSymptom> {

	List<WmkgReasonSymptom> getReasonSymptom(String name);
}
