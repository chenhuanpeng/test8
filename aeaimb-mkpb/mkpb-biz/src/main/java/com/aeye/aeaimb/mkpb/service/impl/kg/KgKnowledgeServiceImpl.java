package com.aeye.aeaimb.mkpb.service.impl.kg;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.aeye.aeaimb.common.security.util.SecurityUtils;
import com.aeye.aeaimb.mkpb.entity.sys.SysDictItem;
import com.aeye.aeaimb.mkpb.mapper.kg.KgKnowledgeMapper;
import com.aeye.aeaimb.mkpb.service.kg.*;
import com.aeye.cdss.admin.api.dto.GeneralPagingOfKnowledgeBase;
import com.aeye.cdss.admin.api.dto.KnowledgeCount;
import com.aeye.cdss.admin.api.dto.KnowledgeDiseaseDTO;
import com.aeye.cdss.admin.api.dto.KnowledgePreviewDTO;
import com.aeye.cdss.admin.api.entity.KgAttributes;
import com.aeye.cdss.admin.api.entity.KgKnowledge;
import com.aeye.cdss.admin.api.entity.KgKnowledgeAsocTag;
import com.aeye.cdss.admin.api.entity.KgTag;
import com.aeye.cdss.admin.api.vo.KgKnowledgeFieldVO;
import com.aeye.cdss.admin.api.vo.KnowledgeListInParameter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 知识
 *
 * @author cdss
 * @date 2023-12-06 17:14:23
 */
@Service
public class KgKnowledgeServiceImpl extends ServiceImpl<KgKnowledgeMapper, KgKnowledge> implements KgKnowledgeService {


	@Autowired
	public KgKnowledgeFieldService kgKnowledgeFieldService;

	@Autowired
	public KgAttributesService kgAttributesService;

	@Autowired
	public KgTagService kgTagService;

	@Autowired
	public KgKnowledgeAsocTagService kgKnowledgeAsocTagService;

	@Override
	public List<KnowledgeCount> knowledgePaging() {
		return this.baseMapper.homePage();
	}

	@Override
	public IPage<KnowledgeDiseaseDTO> getDiseaseList(Page page, KnowledgeListInParameter kgKnowledge) {
		return this.baseMapper.getDiseaseList(page, kgKnowledge);
	}

	@Override
	public IPage<KnowledgeDiseaseDTO> getUniversalPaging(Page page, String type, String name, List<GeneralPagingOfKnowledgeBase> lookup, List<GeneralPagingOfKnowledgeBase> where) {

		IPage<KnowledgeDiseaseDTO> pageRecord = new Page<>();
		Long count = baseMapper.countUniversalPaging(type,name);
		if (null == count) {
			return pageRecord;
		}
		pageRecord.setTotal(count);
		Long totalPages = (count + page.getSize() - 1) / page.getSize();
		pageRecord.setPages(totalPages);
		if (page.getCurrent() <= 1) {
			page.setCurrent(0);
		} else {
			page.setCurrent((page.getCurrent()-1) * page.getSize());
		}
		List<KnowledgeDiseaseDTO> universalPaging = baseMapper.getUniversalPaging(type, name, lookup, where, page.getCurrent(), page.getSize());
		pageRecord.setRecords(universalPaging);
		return pageRecord;
	}

	@Override
	public Map<String, List<GeneralPagingOfKnowledgeBase>> setKeys(KnowledgeListInParameter kgKnowledge) {
		Map<String, List<GeneralPagingOfKnowledgeBase>> map = new HashMap<>();
		List<GeneralPagingOfKnowledgeBase> lookup = new ArrayList<>();
		List<GeneralPagingOfKnowledgeBase> where = new ArrayList<>();

		switch (kgKnowledge.getType()) {
			case "T1":
				lookup.add(new GeneralPagingOfKnowledgeBase("administrativeOffice", "T1-9-0128"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T1-47-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getAdministrativeOffice())) {
					where.add(new GeneralPagingOfKnowledgeBase("administrativeOffice", "T1-9-0128", kgKnowledge.getAdministrativeOffice()));
				}
				break;
			case "T2":
				lookup.add(new GeneralPagingOfKnowledgeBase("rangeOfApplication", "T2-4-0183"));
				lookup.add(new GeneralPagingOfKnowledgeBase("issueYear", "T2-3-0051"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T2-36-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getRangeOfApplication())) {
					where.add(new GeneralPagingOfKnowledgeBase("rangeOfApplication", "T2-5-0051", kgKnowledge.getRangeOfApplication()));
				}
				break;
			case "T3":
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T3-31-0267"));
				lookup.add(new GeneralPagingOfKnowledgeBase("issueYear", "T3-3-0051"));
				lookup.add(new GeneralPagingOfKnowledgeBase("publisher", "T3-4-0052"));
				break;
			case "T4":
				lookup.add(new GeneralPagingOfKnowledgeBase("classificationOfDiseases", "T4-3-0083"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T4-17-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getClassificationOfDiseases())) {
					where.add(new GeneralPagingOfKnowledgeBase("classificationOfDiseases", "T4-4-0097", kgKnowledge.getClassificationOfDiseases()));
				}
				break;
			case "T5":
				lookup.add(new GeneralPagingOfKnowledgeBase("classificationOfSymptoms", "T5-6-0073"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T5-27-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getClassificationOfSymptoms())) {
					where.add(new GeneralPagingOfKnowledgeBase("classificationOfSymptoms", "T5-7-0091", kgKnowledge.getClassificationOfSymptoms()));
				}
				break;
			case "T6":
				lookup.add(new GeneralPagingOfKnowledgeBase("drugType", "T6-8-0227"));
				lookup.add(new GeneralPagingOfKnowledgeBase("prescriptionType", "T6-12-0041"));
				lookup.add(new GeneralPagingOfKnowledgeBase("typesOfChineseAndWesternMedicine", "T6-14-0281"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T6-54-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getDrugType())) {
					where.add(new GeneralPagingOfKnowledgeBase("drugType", "T6-9-0228", kgKnowledge.getDrugType()));
				}
				if (StringUtils.isNotBlank(kgKnowledge.getPrescriptionType())) {
					where.add(new GeneralPagingOfKnowledgeBase("prescriptionType", "T6-13-0042", kgKnowledge.getPrescriptionType()));
				}
				if (StringUtils.isNotBlank(kgKnowledge.getTypesOfChineseAndWesternMedicine())) {
					where.add(new GeneralPagingOfKnowledgeBase("typesOfChineseAndWesternMedicine", "T6-15-0282", kgKnowledge.getTypesOfChineseAndWesternMedicine()));
				}
				break;
			case "T7":
				lookup.add(new GeneralPagingOfKnowledgeBase("typeOfOperation", "T7-7-0193"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T7-31-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getTypeOfOperation())) {
					where.add(new GeneralPagingOfKnowledgeBase("typeOfOperation", "T7-8-0194", kgKnowledge.getTypeOfOperation()));
				}
				break;
			case "T8":
				lookup.add(new GeneralPagingOfKnowledgeBase("checkType", "T8-6-0093"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T8-42-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getCheckType())) {
					where.add(new GeneralPagingOfKnowledgeBase("checkType", "T8-7-0094", kgKnowledge.getCheckType()));
				}
				break;
			case "T9":
				lookup.add(new GeneralPagingOfKnowledgeBase("checkType", "T9-6-0103"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T9-34-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getCheckType())) {
					where.add(new GeneralPagingOfKnowledgeBase("checkType", "T9-7-0104", kgKnowledge.getCheckoutType()));
				}
				break;
			case "T10":
				lookup.add(new GeneralPagingOfKnowledgeBase("operationType", "T10-6-0027"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T10-22-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getOperationType())) {
					where.add(new GeneralPagingOfKnowledgeBase("operationType", "T10-7-0028", kgKnowledge.getOperationType()));
				}
				break;
			case "T11":
				lookup.add(new GeneralPagingOfKnowledgeBase("scaleType", "T11-6-0137"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T11-17-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getScaleType())) {
					where.add(new GeneralPagingOfKnowledgeBase("scaleType", "T11-7-0138", kgKnowledge.getScaleType()));
				}
				break;
			case "T12":

				break;
			case "T13":

				break;
			case "T14":
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T14-9-0267"));
				break;
			case "T15":
				lookup.add(new GeneralPagingOfKnowledgeBase("documentType", "T15-3-0214"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T15-14-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getDocumentType())) {
					where.add(new GeneralPagingOfKnowledgeBase("documentType", "T15-4-0215", kgKnowledge.getLegalType()));
				}
				break;
			case "T16":
				lookup.add(new GeneralPagingOfKnowledgeBase("legalType", "T16-6-0267"));
				lookup.add(new GeneralPagingOfKnowledgeBase("source", "T16-6-0267"));
				if (StringUtils.isNotBlank(kgKnowledge.getDocumentType())) {
					where.add(new GeneralPagingOfKnowledgeBase("legalType", "T16-7-0268", kgKnowledge.getLegalType()));
				}
				break;
		}

		map.put("lookup", lookup);
		map.put("where", where);
		return map;
	}

	@Override
	public List<KgKnowledgeFieldVO> getsTheListOfNewItems(String kgid) {
		KgKnowledge kk = this.getById(kgid);
		if (BeanUtil.isNotEmpty(kk)) {
			List<KgKnowledgeFieldVO> list = this.baseMapper.getsTheListOfNewItems(kgid, kk.getType());
			if (CollectionUtil.isNotEmpty(list)) {
				list.get(0).setVal(kk.getId());
				list.get(1).setVal(kk.getName());


				int is = 0;
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getFieldDataType().equals("rich")) {
						is = i;
						break;
					}
				}
				// 获取最后两个元素
				KgKnowledgeFieldVO lastElement = list.get(list.size() - 1);
				KgKnowledgeFieldVO secondLastElement = list.get(list.size() - 2);

				// 指定的索引位置

				// 在指定索引处插入元素
				list.add(is, secondLastElement);
				list.add(is + 1, lastElement);

				// 删除最后两个元素（因为它们已经被复制到新位置）
				list.remove(list.size() - 1);
				list.remove(list.size() - 1);

			}
			return list;
		}
		return null;
	}

	@Override
	public KnowledgePreviewDTO getPreviewData(String kgid) {
		KgKnowledge kk = this.getById(kgid);
		if (BeanUtil.isNotEmpty(kk)) {
			KnowledgePreviewDTO kpd = new KnowledgePreviewDTO();
			List<KgKnowledgeFieldVO> list = this.baseMapper.getPreviewData(kgid, kk.getType());
			kpd.setId(kk.getId());
			kpd.setName(kk.getName());
			kpd.setBaseInfo(new ArrayList<>());
			kpd.setRichText(new ArrayList<>());
			for (KgKnowledgeFieldVO kv : list) {
				//此处这个写法不知道干嘛的
//				if (kv.getFieldName().equals("概述")){
//					kpd.setOverview(kv.getVal());
//				}else
				if (kv.getFieldBaseInfo().equals(1)) {
					kpd.getBaseInfo().add(kv);
				} else {
					kpd.getRichText().add(kv);
				}
			}
			return kpd;
		}
		return null;
	}

	@Override
	@Transactional
//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean modifyAndAdd(String kgid, List<KgKnowledgeFieldVO> list, Map<String, Map<String, SysDictItem>> disMap) {
		if (CollectionUtil.isEmpty(list) || list.size() == 0) {
			return false;
		}
		if (StringUtils.isBlank(kgid)) {
			String type = list.get(0).getTypeCode();
			List<KgKnowledgeFieldVO> templateList = kgKnowledgeFieldService.getAListOfItems(type, false);
			Map<String, KgKnowledgeFieldVO> map = list.stream()
					.collect(Collectors.toMap(KgKnowledgeFieldVO::getFieldCode, obj -> obj));
			KgKnowledge kk = new KgKnowledge();
			kk.setType(type);
			kk.setPublishDate(LocalDateTime.now());
			kk.setCreateBy(SecurityUtils.getUser().getName());
			kk.setUpdateBy(SecurityUtils.getUser().getName());
			this.save(kk);
			kgid = kk.getId();
			String finalKgid = kgid;
			String name = null;
			for (int i = 0; i < templateList.size(); i++) {
				KgKnowledgeFieldVO c = templateList.get(i);
				KgKnowledgeFieldVO base = map.get(c.getFieldCode());
				if (base != null) {
					warehousingClassification(finalKgid, base, null);
				}
				if (c.getIdx() == 2) {
					kk.setName(base.getVal());
				}


				if (BeanUtil.isNotEmpty(map.get(c.getFieldCode())) && StringUtils.isNotEmpty(map.get(c.getFieldCode()).getVal())) {


					if (StringUtils.isNotBlank(base.getVal())) {
						KgAttributes ka = new KgAttributes();
						ka.setName(c.getFieldName());
						ka.setType(c.getFieldDataType());
						ka.setValue(base.getVal());
						ka.setIdx(c.getIdx());
						ka.setKgId(finalKgid);
						ka.setFieldCode(c.getFieldCode());
						if (c.getIdx() == 1) {
							ka.setValue(finalKgid);
						}
						kgAttributesService.save(ka);
					}
				} else {
					String val = null;
					if (c.getFieldDataType().trim().equals("select")
							&& templateList.size() > i + 1
							&& templateList.get(i + 1) != null && map.containsKey(templateList.get(i + 1).getFieldCode())
							&& templateList.get(i + 1).getFieldDataType().trim().equals("select-code")
							&& templateList.get(i + 1).getFieldDict().equals(c.getFieldDict())
							&& StringUtils.isNotBlank(map.get(templateList.get(i + 1).getFieldCode()).getVal())
					) {
						SysDictItem sy = disMap.containsKey(c.getFieldDict()) ? disMap.get(c.getFieldDict()).get(map.get(templateList.get(i + 1).getFieldCode()).getVal()) : null;

//						disMap.containsKey(c.getFieldDict())?
//								disMap.get(c.getFieldDict()).containsKey(templateList.get(i+1).getFieldCode())?disMap.get(c.getFieldDict()).get(templateList.get(i+1).getFieldCode()).getLabel()?null
//										:null;
						val = sy != null ? sy.getLabel() : null;
					}

					if (c.getFieldDataType().trim().equals("treeSelect")
							&& templateList.size() > i + 1
							&& templateList.get(i + 1) != null && map.containsKey(templateList.get(i + 1).getFieldCode())
							&& templateList.get(i + 1).getFieldDataType().trim().equals("treeSelect-code")
							&& templateList.get(i + 1).getFieldDict().equals(c.getFieldDict())
							&& StringUtils.isNotBlank(map.get(templateList.get(i + 1).getFieldCode()).getVal())
					) {
						KgTag kt = kgTagService.getById(map.get(templateList.get(i + 1).getFieldCode()).getVal());
						val = kt != null ? kt.getName() : null;
					}


					if (val != null) {
						KgAttributes ka = new KgAttributes();
						ka.setName(c.getFieldName());
						ka.setType(c.getFieldDataType());
						ka.setValue(val);
						ka.setIdx(c.getIdx());
						ka.setKgId(finalKgid);
						ka.setFieldCode(c.getFieldCode());
						if (c.getIdx() == 1) {
							ka.setValue(finalKgid);
						}
						kgAttributesService.save(ka);
					}

				}
			}

			this.updateById(kk);

		} else {
			KgKnowledge kk = this.getById(kgid);
			if (BeanUtil.isNotEmpty(kk)) {
				kk.setPublishDate(LocalDateTime.now());
				// 使用Stream API从List中创建Map
				Map<String, KgKnowledgeFieldVO> map = list.stream()
						.collect(Collectors.toMap(KgKnowledgeFieldVO::getFieldCode, obj -> obj));
				List<KgKnowledgeFieldVO> templateList = getsTheListOfNewItems(kgid);//kgKnowledgeFieldService.getAListOfItems(kk.getType());
				String finalKgid1 = kgid;
				for (int i = 0; i < templateList.size(); i++) {
					KgKnowledgeFieldVO c = templateList.get(i);
					LambdaQueryWrapper<KgAttributes> lambdaQueryWrapper = new LambdaQueryWrapper<>();
					lambdaQueryWrapper.eq(KgAttributes::getKgId, finalKgid1);
					lambdaQueryWrapper.eq(KgAttributes::getFieldCode, c.getFieldCode());
					kgAttributesService.remove(lambdaQueryWrapper);
					KgKnowledgeFieldVO base = map.get(c.getFieldCode());
					if (BeanUtil.isNotEmpty(map.get(c.getFieldCode())) && StringUtils.isNotEmpty(map.get(c.getFieldCode()).getVal())) {

						warehousingClassification(finalKgid1, base, c);
						if (c.getIdx() == 2) {
							kk.setName(base.getVal());
						}
						if (StringUtils.isNotBlank(base.getVal())) {
							KgAttributes ka = new KgAttributes();
							ka.setName(c.getFieldName());
							ka.setType(c.getFieldDataType());
							ka.setValue(base.getVal());
							ka.setIdx(c.getIdx());
							ka.setKgId(finalKgid1);
							ka.setFieldCode(c.getFieldCode());
							kgAttributesService.save(ka);
						}
					} else {
						String val = null;

						if (c.getFieldDataType().trim().equals("select")
								&& templateList.size() > i + 1
								&& templateList.get(i + 1) != null && map.containsKey(templateList.get(i + 1).getFieldCode())
								&& templateList.get(i + 1).getFieldDataType().trim().equals("select-code")
								&& templateList.get(i + 1).getFieldDict().equals(c.getFieldDict())
								&& StringUtils.isNotBlank(map.get(templateList.get(i + 1).getFieldCode()).getVal())
						) {
//							SysDictItem sy =remoteDictService.getDictionarys(c.getFieldDict(),map.get(templateList.get(i+1).getFieldCode()).getVal());
							SysDictItem sy = disMap.containsKey(c.getFieldDict()) ? disMap.get(c.getFieldDict()).get(map.get(templateList.get(i + 1).getFieldCode()).getVal()) : null;
							val = sy != null ? sy.getLabel() : null;
						}
						if (c.getFieldDataType().trim().equals("treeSelect")
								&& templateList.size() > i + 1
								&& templateList.get(i + 1) != null && map.containsKey(templateList.get(i + 1).getFieldCode())
								&& templateList.get(i + 1).getFieldDataType().trim().equals("treeSelect-code")
								&& templateList.get(i + 1).getFieldDict().equals(c.getFieldDict())
								&& StringUtils.isNotBlank(map.get(templateList.get(i + 1).getFieldCode()).getVal())
						) {
							KgTag kt = kgTagService.getById(map.get(templateList.get(i + 1).getFieldCode()).getVal());
							val = kt != null ? kt.getName() : null;
						}


						if (val != null) {
							KgAttributes ka = new KgAttributes();
							ka.setName(c.getFieldName());
							ka.setType(c.getFieldDataType());
							ka.setValue(val);
							ka.setIdx(c.getIdx());
							ka.setKgId(finalKgid1);
							ka.setFieldCode(c.getFieldCode());
							if (c.getIdx() == 1) {
								ka.setValue(finalKgid1);
							}
							kgAttributesService.save(ka);
						}

					}
				}
				kk.setUpdateBy(SecurityUtils.getMkpbUser().getName());
				this.updateById(kk);
			}
		}

		return true;
	}


	private void warehousingClassification(String kgid, KgKnowledgeFieldVO base, KgKnowledgeFieldVO old) {
		String[] st = new String[]{"T1-8-0084", "T2-10-0084", "T3-10-0084", "T3-8-0271", "T4-4-0084", "T5-7-0265", "T6-9-0228", "T7-8-0194", "T8-7-0094", "T9-7-0104"};
		if (Arrays.binarySearch(st, base.getFieldCode()) >= 0) {
			if (old != null && StringUtils.isNotBlank(old.getVal())) {
				List<String> pd = kgTagService.findAllParentIds(old.getVal());
				for (String tagid : pd) {
					LambdaQueryWrapper<KgKnowledgeAsocTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
					lambdaQueryWrapper.eq(KgKnowledgeAsocTag::getTagId, tagid).eq(KgKnowledgeAsocTag::getKgId, kgid);
					kgKnowledgeAsocTagService.remove(lambdaQueryWrapper);
				}
			}
			if (base != null && StringUtils.isNotBlank(base.getVal())) {
				List<String> pd = kgTagService.findAllParentIds(base.getVal());
				for (String tagid : pd) {
					KgKnowledgeAsocTag kgKnowledgeAsocTag = new KgKnowledgeAsocTag();
					kgKnowledgeAsocTag.setTagId(tagid);
					kgKnowledgeAsocTag.setKgId(kgid);
					kgKnowledgeAsocTagService.save(kgKnowledgeAsocTag);
				}
			}

		}
	}


	@Override
	public Boolean deleteKnowledge(String kgid) {
		if (StringUtils.isNotBlank(kgid)) {

			KgKnowledge kk = this.getById(kgid);
			if (BeanUtil.isNotEmpty(kk)) {
				LambdaQueryWrapper<KgAttributes> lambdaQueryWrapper = new LambdaQueryWrapper<>();
				lambdaQueryWrapper.eq(KgAttributes::getKgId, kgid);
				kgAttributesService.remove(lambdaQueryWrapper);
				this.removeById(kgid);
			}
		}
		return null;
	}
}