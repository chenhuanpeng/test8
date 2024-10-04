package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgGraphMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgGraphMappingErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgGraphMapping;
import com.aeye.aeaimb.hugegraph.mapper.WmkgGraphMappingMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgGraphMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 图谱标准字典映射(BaseGraphMapping)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:03
 */
@Service("baseGraphMappingService")
@Slf4j
public class WmkgGraphMappingServiceImpl extends ServiceImpl<WmkgGraphMappingMapper, WmkgGraphMapping> implements WmkgGraphMappingService {

	@Override
	public List<WmkgGraphMappingErrorDto> batchGraphMapping(List<WmkgGraphMappingDto> dtoList) {
		log.info("医疗服务图谱数据初始化开始");
		int i = 1;
		for (WmkgGraphMappingDto dto : dtoList){
			log.info("医疗服务图谱数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			WmkgGraphMapping wmkgGraphMapping = new WmkgGraphMapping();
			wmkgGraphMapping.setGraphCode(dto.getGraphCode());
			wmkgGraphMapping.setGraphName(dto.getGraphName());
			wmkgGraphMapping.setMediDictType(dto.getMediDictType());
			wmkgGraphMapping.setStandCode(dto.getStandCode());
			wmkgGraphMapping.setStandName(dto.getStandName());
			this.save(wmkgGraphMapping);
		}
		log.info("医疗服务图谱数据初始化结束");
		return null;
	}
}
