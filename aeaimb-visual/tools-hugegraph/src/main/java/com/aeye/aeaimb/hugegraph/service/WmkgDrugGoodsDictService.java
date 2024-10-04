package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugGoodsDictDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDrugGoodsDict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]药品商品字典(WmkgDrugGoodsDict)表服务接口
 *
 * @author linkeke
 * @since 2024-08-30 09:08:05
 */
public interface WmkgDrugGoodsDictService extends IService<WmkgDrugGoodsDict> {

	List<WmkgDrugDictErrorDto> batchDrugGoodsDict(List<WmkgDrugGoodsDictDto> dtoList);
}
