package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.constant.enums.CommonEnum;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictLabelDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DiseaseDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.mapper.KgKnowledgeMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.*;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDiseaseDictMapper;
import com.aeye.aeaimb.hugegraph.service.*;
import com.aeye.aeaimb.hugegraph.service.domain.CodeName;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 疾病字典(BaseDiseaseDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Service("wmkgDiseaseDictService")
@Slf4j
public class WmkgDiseaseDictServiceImpl extends ServiceImpl<WmkgDiseaseDictMapper, WmkgDiseaseDict> implements WmkgDiseaseDictService {

	@Resource
	private WmkgMedicalTermCategoryService baseMedicalTermCategoryService;

	@Resource
	private WmkgDiseaseInCategoryService baseDiseaseInCategoryService;

	@Resource
	private WmkgMedInsurMappingService baseMedInsurMappingService;
	@Resource
	private WmkgDictKgMappingService wmkgDictKgMappingService;
	@Resource
	private WmkgDiseaseLabelService wmkgDiseaseLabelService;
	@Resource
	private WmkgDiseaseSystemService wmkgDiseaseSystemService;
	@Resource
	private WmkgDiseaseDeptService wmkgDiseaseDeptService;


	@Override
	public List<WmkgDiseaseDictErrorDto> batchDiseaseDict(List<WmkgDiseaseDictDto> dictDtos) {

		//刪除数据
		this.getBaseMapper().delete(Wrappers.lambdaQuery());
		baseDiseaseInCategoryService.getBaseMapper().delete(Wrappers.lambdaQuery());
		baseMedicalTermCategoryService.getBaseMapper().delete(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
				.eq(WmkgMedicalTermCategory::getCateFor, "疾病"));

		log.info("疾病数据初始化开始");
		int i = 1;
		for (WmkgDiseaseDictDto dictDto : dictDtos) {
			log.info("疾病数据初始化数据行数为:" + i + "条，总行数为:" + dictDtos.size());
			i++;
			//章
			WmkgMedicalTermCategory category1 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
					.eq(WmkgMedicalTermCategory::getCateFor, "疾病")
					.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName1()));
			String cateCode1 = null;
			if (Objects.isNull(category1)) {
				WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
				baseMedicalTermCategory.setCateType("章");
				baseMedicalTermCategory.setCateFor("疾病");
				baseMedicalTermCategory.setCateName(dictDto.getCateName1());
				baseMedicalTermCategoryService.save(baseMedicalTermCategory);
				cateCode1 = baseMedicalTermCategory.getCateCode();
			} else {
				cateCode1 = category1.getCateCode();
			}

			//节
			WmkgMedicalTermCategory category2 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
					.eq(WmkgMedicalTermCategory::getCateFor, "疾病")
					.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName2()));
			String cateCode2 = null;
			if (Objects.isNull(category2)) {
				WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
				baseMedicalTermCategory.setCateFor("疾病");
				baseMedicalTermCategory.setCateType("节");
				baseMedicalTermCategory.setCateName(dictDto.getCateName2());
				baseMedicalTermCategory.setCateParentCode(cateCode1);
				baseMedicalTermCategoryService.save(baseMedicalTermCategory);
				cateCode2 = baseMedicalTermCategory.getCateCode();
			} else {
				cateCode2 = category2.getCateCode();
			}

			//类目
			WmkgMedicalTermCategory category3 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
					.eq(WmkgMedicalTermCategory::getCateFor, "疾病")
					.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName3()));
			String cateCode3 = null;
			if (Objects.isNull(category3)) {
				WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
				baseMedicalTermCategory.setCateFor("疾病");
				baseMedicalTermCategory.setCateType("类目");
				baseMedicalTermCategory.setCateName(dictDto.getCateName3());
				baseMedicalTermCategory.setCateParentCode(cateCode2);
				baseMedicalTermCategoryService.save(baseMedicalTermCategory);
				cateCode3 = baseMedicalTermCategory.getCateCode();
			} else {
				cateCode3 = category3.getCateCode();
			}

			//亚目
			String cateCode4 = null;
			if (StrUtil.isNotBlank(dictDto.getCateName4())) {
				WmkgMedicalTermCategory category4 = baseMedicalTermCategoryService.getOne(Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
						.eq(WmkgMedicalTermCategory::getCateFor, "疾病")
						.eq(WmkgMedicalTermCategory::getCateName, dictDto.getCateName4()));
				if (Objects.isNull(category4)) {
					WmkgMedicalTermCategory baseMedicalTermCategory = new WmkgMedicalTermCategory();
					baseMedicalTermCategory.setCateFor("疾病");
					baseMedicalTermCategory.setCateType("亚目");
					baseMedicalTermCategory.setCateName(dictDto.getCateName4());
					baseMedicalTermCategory.setCateParentCode(cateCode3);
					baseMedicalTermCategoryService.save(baseMedicalTermCategory);
					cateCode4 = baseMedicalTermCategory.getCateCode();
				} else {
					cateCode4 = category4.getCateCode();
				}
			}

			//插入疾病字典
			WmkgDiseaseDict one = this.getOne(Wrappers.<WmkgDiseaseDict>lambdaQuery().eq(WmkgDiseaseDict::getDiseaseCode, dictDto.getDiseaseCode()));
			if (Objects.isNull(one)) {
				WmkgDiseaseDict baseDiseaseDict = new WmkgDiseaseDict();
				baseDiseaseDict.setDiseaseCode(dictDto.getDiseaseCode());
				baseDiseaseDict.setDiseaseName(dictDto.getDiseaseName());
				baseDiseaseDict.setNormType(dictDto.getNormType());
				baseDiseaseDict.setCreateBy("imp");
				baseDiseaseDict.setUpdateBy("imp");
				baseDiseaseDict.setCreateTime(new Date());
				baseDiseaseDict.setUpdateTime(new Date());
				this.save(baseDiseaseDict);
			}

			//疾病所属分类
			WmkgDiseaseInCategory baseDiseaseInCategory = new WmkgDiseaseInCategory();
			baseDiseaseInCategory.setDiseaseCode(dictDto.getDiseaseCode());
			baseDiseaseInCategory.setNormType(dictDto.getNormType());
			if (StrUtil.isNotBlank(cateCode1)) {
				baseDiseaseInCategory.setCateCode(cateCode1);
			}
			if (StrUtil.isNotBlank(cateCode2)) {
				baseDiseaseInCategory.setCateCode(cateCode2);
			}
			if (StrUtil.isNotBlank(cateCode3)) {
				baseDiseaseInCategory.setCateCode(cateCode3);
			}
			if (StrUtil.isNotBlank(cateCode4)) {
				baseDiseaseInCategory.setCateCode(cateCode4);
			}
			baseDiseaseInCategoryService.save(baseDiseaseInCategory);

			//医疗医保编码映射
			WmkgMedInsurMapping baseMedInsurMapping = new WmkgMedInsurMapping();
			baseMedInsurMapping.setMedCode(dictDto.getDiseaseCode());
			baseMedInsurMapping.setMedName(dictDto.getDiseaseName());
			baseMedInsurMapping.setMedNormType(dictDto.getNormType());
			baseMedInsurMapping.setInsurCode(dictDto.getInsurCode());
			baseMedInsurMapping.setInsurName(dictDto.getInsurName());
			baseMedInsurMapping.setInsurNormType(dictDto.getInsurNormType());
			baseMedInsurMappingService.save(baseMedInsurMapping);
		}
		log.info("疾病数据初始化完毕");
		return null;
	}

	@Override
	public List<WmkgDiseaseDictErrorDto> batchDiseaseDictLabel(List<WmkgDiseaseDictLabelDto> dictDtos) {
		List<WmkgDiseaseDictErrorDto> errorDtos = new ArrayList<>();
		log.info("疾病标签数据初始化开始");
		int i = 1;
		for (WmkgDiseaseDictLabelDto dictDto : dictDtos){
			log.info("疾病标签数据行数为:" + i + "条，总行数为:" + dictDtos.size());
			i++;
			String diseaseCode = dictDto.getDiseaseCode();
			WmkgDiseaseDict one = this.getOne(Wrappers.<WmkgDiseaseDict>lambdaQuery().eq(WmkgDiseaseDict::getDiseaseCode, diseaseCode));
			if (Objects.isNull(one)){
				log.info(dictDto.getDiseaseCode() +"疾病字典数据为空");
				WmkgDiseaseDictErrorDto errorDto = new WmkgDiseaseDictErrorDto();
				errorDto.setDiseaseCode(dictDto.getDiseaseCode());
				errorDto.setDiseaseName(dictDto.getDiseaseName());
				errorDto.setNormType(dictDto.getNormType());
				errorDto.setErrorMsg("疾病字典数据为空");
				errorDtos.add(errorDto);
				continue;
			}

			//标签
			if (StrUtil.isNotBlank(dictDto.getLabel())){
				String label = dictDto.getLabel();
				String[] split = label.split(",");
                for (String labelTxt : split) {
                    WmkgDiseaseLabel baseDiseaseLabel = new WmkgDiseaseLabel();
                    baseDiseaseLabel.setDiseaseCode(dictDto.getDiseaseCode());
                    baseDiseaseLabel.setDiseaseLabel(extractCriticalPart(dictDto,labelTxt).getName());
					baseDiseaseLabel.setDiseaseLabelAs(CommonEnum.DiseaseLabelValue.getByValue(baseDiseaseLabel.getDiseaseLabel()).getMessage());
					baseDiseaseLabel.setDiseaseSort(CommonEnum.DiseaseLabelValue.getByValue(baseDiseaseLabel.getDiseaseLabel()).getSort());
                    wmkgDiseaseLabelService.save(baseDiseaseLabel);
                }
			}

			//系统
			if (StrUtil.isNotBlank(dictDto.getSystemName())){
				String systemName = dictDto.getSystemName();
				String[] split = systemName.split(",");
				for (String systemNameTxt : split) {
					WmkgDiseaseSystem wmkgDiseaseSystem = new WmkgDiseaseSystem();
					wmkgDiseaseSystem.setDiseaseCode(dictDto.getDiseaseCode());
					wmkgDiseaseSystem.setDiseaseSystem(extractCriticalPart(dictDto,systemNameTxt).getName());
					wmkgDiseaseSystemService.save(wmkgDiseaseSystem);
				}
			}

			//科室
			if (StrUtil.isNotBlank(dictDto.getDeptName())){
				String deptName =  dictDto.getDeptName();
				String[] split = deptName.split(",");
				for (String deptNameTxt : split) {
					WmkgDiseaseDept baseDiseaseDept = new WmkgDiseaseDept();
					baseDiseaseDept.setDiseaseCode(dictDto.getDiseaseCode());
					baseDiseaseDept.setDeptCode(extractCriticalPart(dictDto,deptNameTxt).getCode());
					baseDiseaseDept.setDeptName(extractCriticalPart(dictDto,deptNameTxt).getName());
					wmkgDiseaseDeptService.save(baseDiseaseDept);
				}
			}
		}
		return errorDtos;
	}


	/**
	 * 从给定的字符串中提取出关键部分
	 *
	 * @param input 输入的字符串
	 * @return 返回提取出来的关键部分
	 */
	public  CodeName extractCriticalPart(WmkgDiseaseDictLabelDto dictDto,String input){
		if (input == null || input.isEmpty()) {
			throw new IllegalArgumentException("Input cannot be null or empty.");
		}
		try {
			String[] split = input.split("\\|");
			CodeName codeName = new CodeName();
			codeName.setCode(split[1]);
			codeName.setName(split[0]);
			return codeName;
		}catch (Exception e){
			log.info("错误字符:{},对象：{}",input, JSON.toJSONString(dictDto));
			throw new RuntimeException("提取关键部分时发生错误");
		}
	}

	@Override
	public List<WmkgDiseaseDictErrorDto> batchDiseaseDictKgMapping(List<DiseaseDictKgMappingDto> dictDtos) {

		log.info("疾病映射知识库数据初始化开始");
		int i = 1;
		for (DiseaseDictKgMappingDto dictDto : dictDtos) {
			log.info("疾病映射知识库数据行数为:" + i + "条，总行数为:" + dictDtos.size());
			i++;
			String id = dictDto.getId();
			if (StrUtil.isBlank(id)){
				log.info(dictDto.getDiseaseCode() +"疾病映射知识库数据为空");
				continue;
			}
			WmkgDictKgMapping dictKgMapping = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
					.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getDiseaseCode())
					.eq(WmkgDictKgMapping::getKgId, id));
			if (Objects.isNull(dictKgMapping)){
				WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
				baseDictKgMapping.setMediDictCode(dictDto.getDiseaseCode());
				baseDictKgMapping.setKgId(id);
				baseDictKgMapping.setKgName(dictDto.getName());
				baseDictKgMapping.setKgType("T1");
				baseDictKgMapping.setMediDictName(dictDto.getDiseaseName());
				baseDictKgMapping.setCreateBy("imp");
				baseDictKgMapping.setUpdateBy("imp");
				baseDictKgMapping.setCreateTime(new Date());
				baseDictKgMapping.setUpdateTime(new Date());
				wmkgDictKgMappingService.save(baseDictKgMapping);
			}

			//临床指南
			if (StrUtil.isNotBlank(dictDto.getClinicalGuideID())){
				WmkgDictKgMapping ClinicalGuide = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
						.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getDiseaseCode())
						.eq(WmkgDictKgMapping::getKgId, dictDto.getClinicalGuideID()));
				if (Objects.isNull(ClinicalGuide)) {
					WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
					baseDictKgMapping.setMediDictCode(dictDto.getDiseaseCode());
					baseDictKgMapping.setKgId(dictDto.getClinicalGuideID());
					baseDictKgMapping.setKgName(dictDto.getClinicalGuideName());
					baseDictKgMapping.setKgType("T3");
					baseDictKgMapping.setMediDictName(dictDto.getDiseaseName());
					baseDictKgMapping.setCreateBy("imp");
					baseDictKgMapping.setUpdateBy("imp");
					baseDictKgMapping.setCreateTime(new Date());
					baseDictKgMapping.setUpdateTime(new Date());
					wmkgDictKgMappingService.save(baseDictKgMapping);
				}
			}

			//临床路径
			if (StrUtil.isNotBlank(dictDto.getClinicalPathID())){
				WmkgDictKgMapping ClinicalGuide = wmkgDictKgMappingService.getOne(Wrappers.<WmkgDictKgMapping>lambdaQuery()
						.eq(WmkgDictKgMapping::getMediDictCode, dictDto.getDiseaseCode())
						.eq(WmkgDictKgMapping::getKgId, dictDto.getClinicalPathID()));
				if (Objects.isNull(ClinicalGuide)) {
					WmkgDictKgMapping baseDictKgMapping = new WmkgDictKgMapping();
					baseDictKgMapping.setMediDictCode(dictDto.getDiseaseCode());
					baseDictKgMapping.setKgId(dictDto.getClinicalPathID());
					baseDictKgMapping.setKgName(dictDto.getClinicalPathName());
					baseDictKgMapping.setKgType("T2");
					baseDictKgMapping.setMediDictName(dictDto.getDiseaseName());
					baseDictKgMapping.setCreateBy("imp");
					baseDictKgMapping.setUpdateBy("imp");
					baseDictKgMapping.setCreateTime(new Date());
					baseDictKgMapping.setUpdateTime(new Date());
					wmkgDictKgMappingService.save(baseDictKgMapping);
				}
			}

		}
		log.info("疾病映射知识库数据初始化结束");
		return null;
	}

	@Override
	public WmkgDiseaseDict getDiseaseDict(String diseaseName) {
		return this.getOne(Wrappers.<WmkgDiseaseDict>lambdaQuery().eq(WmkgDiseaseDict::getDiseaseName, diseaseName));
	}
}
