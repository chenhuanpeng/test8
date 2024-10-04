package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSynonymsDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSynonymsErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgSynonyms;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]同义词(WmkgSynonyms)表服务接口
 *
 * @author linkeke
 * @since 2024-07-17 13:33:23
 */
public interface WmkgSynonymsService extends IService<WmkgSynonyms> {

	List<WmkgSynonymsErrorDto> initSynonyms(List<WmkgSynonymsDto> schemaDtoList);

}
