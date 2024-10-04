package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugGoodsDictDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDrugGoodsDict;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDrugGoodsDictMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDrugGoodsMapping;
import com.aeye.aeaimb.hugegraph.service.WmkgDrugGoodsDictService;
import com.aeye.aeaimb.hugegraph.service.WmkgDrugGoodsMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * [西医]药品商品字典(WmkgDrugGoodsDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-30 09:08:05
 */
@Service("wmkgDrugGoodsDictService")
@Slf4j
public class WmkgDrugGoodsDictServiceImpl extends ServiceImpl<WmkgDrugGoodsDictMapper, WmkgDrugGoodsDict> implements WmkgDrugGoodsDictService {
	@Resource
	private WmkgDrugGoodsDictMapper wmkgDrugGoodsDictMapper;
	@Resource
	private WmkgDrugGoodsMappingService wmkgDrugGoodsMappingService;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<WmkgDrugDictErrorDto> batchDrugGoodsDict(List<WmkgDrugGoodsDictDto> dtoList) {

		log.info("注册药品数据初始化开始");
		int i = 1;
		//西药以X开头,取前10位字符作为通用药品字典编码，中药以Z开头，取前11位作为通用药品字典编码
		for (WmkgDrugGoodsDictDto dto : dtoList){
			log.info("注册药品数据处理行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			if (StrUtil.isBlank(dto.getDrugCode())){
				continue;
			}
			if ("无".equals(dto.getDrugNameGe())){
				dto.setDrugNameGe("");
			}
			WmkgDrugGoodsDict entity = new WmkgDrugGoodsDict();
			BeanUtil.copyProperties(dto,entity);
			entity.setCreateBy("imp");
			entity.setUpdateBy("imp");
			entity.setCreateTime(LocalDateTime.now());
			entity.setUpdateTime(LocalDateTime.now());
			this.save(entity);

			//通用药品跟商品字典关联
			WmkgDrugGoodsMapping drugGoodsMapping = new WmkgDrugGoodsMapping();
			String drugCode = dto.getDrugCode();
			drugGoodsMapping.setDrugGoodsCode(drugCode);
			if (drugCode.startsWith("X")){
				drugGoodsMapping.setDrugCode(drugCode.substring(0,10));
			}
			if (drugCode.startsWith("Z")){
				drugGoodsMapping.setDrugCode(drugCode.substring(0,11));
			}
			wmkgDrugGoodsMappingService.save(drugGoodsMapping);
		}

		log.info("注册药品数据初始化结束");

		return null;
	}
}
