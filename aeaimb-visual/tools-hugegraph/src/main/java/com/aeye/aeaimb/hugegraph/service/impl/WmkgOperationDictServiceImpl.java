package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgOperateDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgOperateDictErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.WmkgOperationDictMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgMedInsurMapping;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgMedicalTermCategory;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgOperationDict;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgOperationInCategory;
import com.aeye.aeaimb.hugegraph.service.WmkgMedInsurMappingService;
import com.aeye.aeaimb.hugegraph.service.WmkgMedicalTermCategoryService;
import com.aeye.aeaimb.hugegraph.service.WmkgOperationDictService;
import com.aeye.aeaimb.hugegraph.service.WmkgOperationInCategoryService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 手术字典(BaseOperationDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 15:15:36
 */
@Service("baseOperationDictService")
@Slf4j
public class WmkgOperationDictServiceImpl extends ServiceImpl<WmkgOperationDictMapper, WmkgOperationDict> implements WmkgOperationDictService {
	@Resource
	private WmkgMedicalTermCategoryService baseMedicalTermCategoryService;

	@Resource
	private WmkgOperationInCategoryService baseOperationInCategoryService;

	@Resource
	private WmkgMedInsurMappingService baseMedInsurMappingService;


	@Override
	public List<WmkgOperateDictErrorDto> batchOperationDict(List<WmkgOperateDictDto> dictDtos) {

		//清空数据
		this.getBaseMapper().delete(Wrappers.lambdaQuery());
		baseOperationInCategoryService.getBaseMapper().delete(Wrappers.lambdaQuery());
		baseMedInsurMappingService.getBaseMapper().delete(Wrappers.lambdaQuery());
		baseMedicalTermCategoryService.getBaseMapper().delete(Wrappers.<WmkgMedicalTermCategory>lambdaQuery().eq(WmkgMedicalTermCategory::getCateFor, "手术"));

		log.info("手术数据初始化开始");
		int i = 1;
		for (WmkgOperateDictDto dictDto : dictDtos) {
			log.info("手术数据初始化数据行数为:" + i + "条，总行数为:" + dictDtos.size());
			i++;

			String cateName1 = dictDto.getCateName1();
			String cateCode1 = null;
			if (StrUtil.isNotBlank(cateName1)) {
				//章
				WmkgMedicalTermCategory category1 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
						.eq(WmkgMedicalTermCategory::getCateFor, "手术")
						.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName1()));
				if (Objects.isNull(category1)) {
					WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
					baseMedicalTermCategory.setCateType("章");
					baseMedicalTermCategory.setCateFor("手术");
					baseMedicalTermCategory.setCateName(dictDto.getCateName1());
					baseMedicalTermCategoryService.save(baseMedicalTermCategory);
					cateCode1 = baseMedicalTermCategory.getCateCode();
				} else {
					cateCode1 = category1.getCateCode();
				}
			}

			//节
			String cateName2 = dictDto.getCateName2();
			String cateCode2 = null;
			if (StrUtil.isNotBlank(cateName2)) {
				WmkgMedicalTermCategory category2 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
						.eq(WmkgMedicalTermCategory::getCateFor, "手术")
						.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName2()));
				if (Objects.isNull(category2)) {
					WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
					baseMedicalTermCategory.setCateFor("手术");
					baseMedicalTermCategory.setCateType("类目");
					baseMedicalTermCategory.setCateName(dictDto.getCateName2());
					baseMedicalTermCategory.setCateParentCode(cateCode1);
					baseMedicalTermCategoryService.save(baseMedicalTermCategory);
					cateCode2 = baseMedicalTermCategory.getCateCode();
				} else {
					cateCode2 = category2.getCateCode();
				}
			}


			//类目
			String cateName3 = dictDto.getCateName3();
			String cateCode3 = null;
			if (StrUtil.isNotBlank(cateName3)) {
				WmkgMedicalTermCategory category3 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
						.eq(WmkgMedicalTermCategory::getCateFor, "手术")
						.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName3()));
				if (Objects.isNull(category3)) {
					WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
					baseMedicalTermCategory.setCateFor("手术");
					baseMedicalTermCategory.setCateType("亚目");
					baseMedicalTermCategory.setCateName(dictDto.getCateName3());
					baseMedicalTermCategory.setCateParentCode(cateCode2);
					baseMedicalTermCategoryService.save(baseMedicalTermCategory);
					cateCode3 = baseMedicalTermCategory.getCateCode();
				} else {
					cateCode3 = category3.getCateCode();
				}
			}

			//亚目
			String cateCode4 = null;
			if (StrUtil.isNotBlank(dictDto.getCateName4())){
				WmkgMedicalTermCategory category4 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
						.eq(WmkgMedicalTermCategory::getCateFor, "手术")
						.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName4()));
				if (Objects.isNull(category4)) {
					WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
					baseMedicalTermCategory.setCateFor("手术");
					baseMedicalTermCategory.setCateType("细目");
					baseMedicalTermCategory.setCateName(dictDto.getCateName4());
					baseMedicalTermCategory.setCateParentCode(cateCode3);
					baseMedicalTermCategoryService.save(baseMedicalTermCategory);
					cateCode4 = baseMedicalTermCategory.getCateCode();
				} else {
					cateCode4 = category4.getCateCode();
				}
			}


			//插入手术字典
			WmkgOperationDict one = this.getOne(Wrappers.<WmkgOperationDict>lambdaQuery().eq(WmkgOperationDict::getOperationCode, dictDto.getOperationCode()));
			if (Objects.isNull(one)){
				WmkgOperationDict baseOperationDict = new WmkgOperationDict();
				baseOperationDict.setOperationCode(dictDto.getOperationCode());
				baseOperationDict.setOperationName(dictDto.getOperationName());
				baseOperationDict.setNormType(dictDto.getNormType());
				baseOperationDict.setCreateBy("imp");
				baseOperationDict.setUpdateBy("imp");
				baseOperationDict.setCreateTime(new Date());
				baseOperationDict.setUpdateTime(new Date());
				this.save(baseOperationDict);
			}

			//疾病所属分类
			WmkgOperationInCategory baseOperationInCategory = new WmkgOperationInCategory();
			baseOperationInCategory.setOperationCode(dictDto.getOperationCode());
			baseOperationInCategory.setNormType(dictDto.getNormType());
			if (StrUtil.isNotBlank(cateCode1)) {
				baseOperationInCategory.setCateCode(cateCode1);
			}
			if (StrUtil.isNotBlank(cateCode2)) {
				baseOperationInCategory.setCateCode(cateCode2);
			}
			if (StrUtil.isNotBlank(cateCode3)) {
				baseOperationInCategory.setCateCode(cateCode3);
			}
			if (StrUtil.isNotBlank(cateCode4)) {
				baseOperationInCategory.setCateCode(cateCode4);
			}
			baseOperationInCategoryService.save(baseOperationInCategory);

			//医疗医保编码映射
			WmkgMedInsurMapping baseMedInsurMapping = new WmkgMedInsurMapping();
			baseMedInsurMapping.setMedCode(dictDto.getOperationCode());
			baseMedInsurMapping.setMedName(dictDto.getOperationName());
			baseMedInsurMapping.setMedNormType(dictDto.getNormType());
			baseMedInsurMapping.setInsurCode(dictDto.getInsurCode());
			baseMedInsurMapping.setInsurName(dictDto.getInsurName());
			baseMedInsurMapping.setInsurNormType(dictDto.getInsurNormType());
			baseMedInsurMappingService.save(baseMedInsurMapping);
		}
		log.info("疾病数据初始化完毕");
		return null;
	}
}
