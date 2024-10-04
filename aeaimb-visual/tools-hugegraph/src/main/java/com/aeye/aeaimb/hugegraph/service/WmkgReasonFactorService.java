package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.DataDto;
import com.aeye.aeaimb.hugegraph.controller.dto.ReasonFactorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SaveDataErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgReasonFactor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 推理因子分类(WmkgReasonFactor)表服务接口
 *
 * @author linkeke
 * @since 2024-08-03 09:36:58
 */
public interface WmkgReasonFactorService extends IService<WmkgReasonFactor> {


	List<SaveDataErrorDto> batchReasonFactor(List<ReasonFactorDto> dtoList);


	List<SaveDataErrorDto> batchDiagReasonObjects(List<DataDto> dtoList);
}
