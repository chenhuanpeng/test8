package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DiseaseDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DrugDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.entity.*;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDrugDictMapper;
import com.aeye.aeaimb.hugegraph.service.*;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 药品字典(BaseDrugDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
@Service("baseDrugDictService")
@Slf4j
public class WmkgDrugDictServiceImpl extends ServiceImpl<WmkgDrugDictMapper, WmkgDrugDict> implements WmkgDrugDictService {

	@Resource
	private WmkgDrugInCategoryService wmkgDrugInCategoryService;

	@Resource
	private WmkgDrugCategoryService wmkgDrugCategoryService;

	@Resource
	private WmkgDictKgMappingService wmkgDictKgMappingService;
	@Override
	public List<WmkgDrugDictErrorDto> batchDrugDict(List<WmkgDrugDictDto> dtoList) {

		//删除药品字典表数据、药品所属分类、药品分类数据
		this.getBaseMapper().delete(Wrappers.lambdaQuery());
		wmkgDrugInCategoryService.getBaseMapper().delete(Wrappers.lambdaQuery());
		wmkgDrugCategoryService.getBaseMapper().delete(Wrappers.lambdaQuery());


		log.info("药品数据初始化开始");
		int i = 1;
		for (WmkgDrugDictDto dto : dtoList){
			log.info("药品数据初始化数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;

			WmkgDrugCategory one1 = wmkgDrugCategoryService.getOne(Wrappers.<WmkgDrugCategory>lambdaQuery()
					.eq(WmkgDrugCategory::getCateCode, dto.getCateCode1())
					.eq(WmkgDrugCategory::getCateName, dto.getCateName1()));
			String cateCode1 = null;
			if (Objects.isNull(one1)) {
				WmkgDrugCategory drugCategory = new WmkgDrugCategory();
				drugCategory.setCateCode(dto.getCateCode1());
				drugCategory.setCateName(dto.getCateName1());
				wmkgDrugCategoryService.save(drugCategory);
				cateCode1 = drugCategory.getCateCode();
			} else {
				cateCode1 = one1.getCateCode();
			}

			//节
			WmkgDrugCategory one2 = wmkgDrugCategoryService.getOne(Wrappers.<WmkgDrugCategory>lambdaQuery()
					.eq(WmkgDrugCategory::getCateCode, dto.getCateCode2())
					.eq(WmkgDrugCategory::getCateName, dto.getCateName2()));
			String cateCode2 = null;
			if (Objects.isNull(one2)) {
				WmkgDrugCategory drugCategory = new WmkgDrugCategory();
				drugCategory.setCateCode(dto.getCateCode2());
				drugCategory.setCateName(dto.getCateName2());
				drugCategory.setParentCode(cateCode1);
				wmkgDrugCategoryService.save(drugCategory);
				cateCode2 = dto.getCateCode2();
			} else {
				cateCode2 = one2.getCateCode();
			}

			//类目
			WmkgDrugCategory one3 = wmkgDrugCategoryService.getOne(Wrappers.<WmkgDrugCategory>lambdaQuery()
					.eq(WmkgDrugCategory::getCateCode, dto.getCateCode3()));
			String cateCode3 = null;
			if (Objects.isNull(one3)) {
				WmkgDrugCategory drugCategory = new WmkgDrugCategory();
				drugCategory.setCateCode(dto.getCateCode3());
				drugCategory.setCateName(dto.getCateName3());
				drugCategory.setParentCode(cateCode2);
				wmkgDrugCategoryService.save(drugCategory);
				cateCode3 = drugCategory.getCateCode();
			} else {
				cateCode3 = one3.getCateCode();
			}

			//亚目
			String cateCode4 = null;
			WmkgDrugCategory one4 = wmkgDrugCategoryService.getOne(Wrappers.<WmkgDrugCategory>lambdaQuery()
					.eq(WmkgDrugCategory::getCateCode, dto.getCateCode4()));
			if (Objects.isNull(one4)) {
				WmkgDrugCategory drugCategory = new WmkgDrugCategory();
				drugCategory.setCateCode(dto.getCateCode4());
				drugCategory.setCateName(dto.getCateName4());
				drugCategory.setParentCode(cateCode3);
				wmkgDrugCategoryService.save(drugCategory);
				cateCode4 = drugCategory.getCateCode();
			} else {
				cateCode4 = one4.getCateCode();
			}

			//插入药品字典
			WmkgDrugDict one = this.getOne(Wrappers.<WmkgDrugDict>lambdaQuery().eq(WmkgDrugDict::getDrugCode, dto.getDrugCode()));
			if (Objects.isNull(one)){
				WmkgDrugDict drugDict = new WmkgDrugDict();
				drugDict.setDrugCode(dto.getDrugCode());
				drugDict.setDrugName(dto.getDrugName());
				drugDict.setDrugNameGe(dto.getDrugName());
				drugDict.setCreateBy("imp");
				drugDict.setUpdateBy("imp");
				drugDict.setCreateTime(new Date());
				drugDict.setUpdateTime(new Date());
				this.save(drugDict);
			}

			//药品所属分类
			WmkgDrugInCategory wmkgDrugInCategory = new WmkgDrugInCategory();
			wmkgDrugInCategory.setDrugCode(dto.getDrugCode());
			if (StrUtil.isNotBlank(cateCode1)) {
				wmkgDrugInCategory.setCateCode(cateCode1);
			}
			if (StrUtil.isNotBlank(cateCode2)) {
				wmkgDrugInCategory.setCateCode(cateCode2);
			}
			if (StrUtil.isNotBlank(cateCode3)) {
				wmkgDrugInCategory.setCateCode(cateCode3);
			}
			if (StrUtil.isNotBlank(cateCode4)) {
				wmkgDrugInCategory.setCateCode(cateCode4);
			}
			wmkgDrugInCategoryService.save(wmkgDrugInCategory);
		}

		return null;
	}

	@Override
	public List<WmkgDrugDictErrorDto> batchDrugDictKgMapping(List<DrugDictKgMappingDto> dtoList) {

		log.info("药品映射知识库数据初始化开始");
		int i = 1;
		for (DrugDictKgMappingDto dictDto : dtoList) {
			log.info("药品映射知识库数据行数为:" + i + "条，总行数为:" + dtoList.size());
			i++;
			String id = dictDto.getId();
			if (StrUtil.isBlank(id)){
				log.info(dictDto.getDrugCode() +"药品映射知识库数据为空");
				continue;
			}
			if (StrUtil.isBlank(dictDto.getDrugCode())){
				log.info(dictDto.getDrugCode() +"药品编码数据为空");
				continue;
			}
			WmkgDictKgMapping dictKgMapping = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
					.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getDrugCode())
					.eq(WmkgDictKgMapping::getKgId, id));
			if (Objects.isNull(dictKgMapping)){
				WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
				baseDictKgMapping.setMediDictCode(dictDto.getDrugCode());
				baseDictKgMapping.setKgId(id);
				baseDictKgMapping.setKgName(dictDto.getName());
				baseDictKgMapping.setKgType("T6");
				baseDictKgMapping.setMediDictName(dictDto.getDrugName());
				baseDictKgMapping.setCreateBy("imp");
				baseDictKgMapping.setUpdateBy("imp");
				baseDictKgMapping.setCreateTime(new Date());
				baseDictKgMapping.setUpdateTime(new Date());
				wmkgDictKgMappingService.save(baseDictKgMapping);
			}
		}
		log.info("疾病映射知识库数据初始化结束");
		return null;
	}
}
