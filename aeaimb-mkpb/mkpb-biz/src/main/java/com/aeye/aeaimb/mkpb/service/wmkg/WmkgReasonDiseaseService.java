package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDisease;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 推理疾病(WmkgReasonDisease)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
public interface WmkgReasonDiseaseService extends IService<WmkgReasonDisease> {

	List<WmkgReasonDisease> getReasonDisease(String name);
}
