package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.mkpb.dto.cdn.AddReasonCategory;
import com.aeye.aeaimb.mkpb.dto.cdn.AddReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.AddReasonGroup;
import com.aeye.aeaimb.mkpb.dto.cdn.AuReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.exam.AddReasonCdnExamDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.multi.AddReasonCdnFrequentDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.symptom.AddReasonCdnSympElmtDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.symptom.AddReasonCdnSymptomDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.test.AddReasonCdnLabtestDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.*;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonCdnMapper;
import com.aeye.aeaimb.mkpb.vo.wmkg.DictInfo;
import com.aeye.aeaimb.mkpb.vo.wmkg.DictItemInfo;
import com.aeye.aeaimb.mkpb.service.wmkg.*;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 推理规则条件(WmkgReasonCdn)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:20
 */
@Service("wmkgReasonCdnService")
@Slf4j
@DS("wmkg")
public class WmkgReasonCdnServiceImpl extends ServiceImpl<WmkgReasonCdnMapper, WmkgReasonCdn> implements WmkgReasonCdnService {
	@Resource
	private WmkgReasonDefineService wmkgReasonDefineService;

	@Resource
	private WmkgReasonCategoryService wmkgReasonCategoryService;

	@Resource
	private WmkgReasonGroupService wmkgReasonGroupService;

	@Resource
	private WmkgReasonCdnSymptomService wmkgReasonCdnSymptomService;

	@Resource
	private WmkgReasonCdnSympElmtService wmkgReasonCdnSympElmtService;

	@Resource
	private WmkgReasonCdnExpService wmkgReasonCdnExpService;

	@Resource
	private WmkgReasonCdnFrequentService wmkgReasonCdnFrequentService;

	@Resource
	private WmkgReasonCdnPhysicalService wmkgReasonCdnPhysicalService;

	@Resource
	private WmkgReasonCdnExamService wmkgReasonCdnExamService;

	@Resource
	private WmkgReasonCdnLabtestService wmkgReasonCdnLabtestService;
	@Resource
	private WmkgReasonCdnMapper wmkgReasonCdnMapper;

	@Resource
	private WmkgReasonFactorService wmkgReasonFactorService;

	@Resource
	private WmkgDictService wmkgDictService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveReasonCdnSymp(AuReasonDefineDTO<AddReasonCdnSymptomDTO> dto) {
		//保存或更新方案表
		AddReasonDefineDTO reasonDefine = dto.getReasonDefine();
		WmkgReasonDefine wmkgReasonDefine = saveReasonDefine(reasonDefine);

		AddReasonCategory<AddReasonCdnSymptomDTO> reasonCategory = dto.getReasonCategory();
		//新增规则分类
		WmkgReasonCategory one = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate()));
		if (Objects.isNull(one)) {
			WmkgReasonCategory wmkgReasonCategory = new WmkgReasonCategory();
			BeanUtil.copyProperties(reasonCategory, wmkgReasonCategory);
			wmkgReasonCategory.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonCategoryService.save(wmkgReasonCategory);
		} else {
			//更新比例值
			wmkgReasonCategoryService.update(Wrappers.<WmkgReasonCategory>lambdaUpdate()
					.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
					.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate())
					.set(WmkgReasonCategory::getFactorRatio, reasonCategory.getFactorRatio()));
		}
		String factorCate = reasonCategory.getFactorCate();

		//保存或更新规则组
		List<AddReasonGroup<AddReasonCdnSymptomDTO>> reasonGroupsForSymp = reasonCategory.getReasonGroups();

		//清除旧数据
		List<WmkgReasonGroup> groupList = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonGroup::getFactorCate, factorCate));
		List<String> groupIds = groupList.stream().map(WmkgReasonGroup::getGroupId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(groupIds)) {
			wmkgReasonGroupService.removeByIds(groupIds);
			List<WmkgReasonCdn> list = this.list(Wrappers.<WmkgReasonCdn>lambdaQuery()
					.in(WmkgReasonCdn::getGroupId, groupIds));
			if (CollUtil.isNotEmpty(list)) {
				List<String> cdnIds = list.stream().map(WmkgReasonCdn::getCdnId).collect(Collectors.toList());
				this.remove(Wrappers.<WmkgReasonCdn>lambdaQuery().in(WmkgReasonCdn::getCdnId, cdnIds));
				wmkgReasonCdnSympElmtService.remove(Wrappers.<WmkgReasonCdnSympElmt>lambdaQuery().in(WmkgReasonCdnSympElmt::getCdnId, cdnIds));
				wmkgReasonCdnSymptomService.remove(Wrappers.<WmkgReasonCdnSymptom>lambdaQuery().in(WmkgReasonCdnSymptom::getCdnId, cdnIds));
			}
		}

		for (AddReasonGroup<AddReasonCdnSymptomDTO> reasonGroup : reasonGroupsForSymp) {
			WmkgReasonGroup wmkgReasonGroup = new WmkgReasonGroup();
			BeanUtil.copyProperties(reasonGroup, wmkgReasonGroup);
			wmkgReasonGroup.setFactorCate(factorCate);
			wmkgReasonGroup.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonGroupService.saveOrUpdate(wmkgReasonGroup);

			//规则组内条件
			List<AddReasonCdnSymptomDTO> reasonCdnsForSymp = reasonGroup.getReasonCdns();
			for (AddReasonCdnSymptomDTO reasonCdn : reasonCdnsForSymp) {
				String symptomMain = reasonCdn.getSymptomMain();
				String symptomCode = reasonCdn.getSymptomCode();
				String symptomName = reasonCdn.getSymptomName();
				String factorWeight = reasonCdn.getFactorWeight();
				Integer factorSort = reasonCdn.getFactorSort();
				List<AddReasonCdnSympElmtDTO> symptomElmtList = reasonCdn.getSymptomElmtList();

				AddReasonCdnSympElmtDTO sympElmtDTO = new AddReasonCdnSympElmtDTO();
				sympElmtDTO.setSymptomName(symptomName);
				sympElmtDTO.setSymptomCode(symptomCode);
				sympElmtDTO.setFactorName("是否主要症状");
				sympElmtDTO.setFactorWeight(factorWeight);
				sympElmtDTO.setFactorValue(symptomMain);
				sympElmtDTO.setFactorSort(factorSort);
				symptomElmtList.add(sympElmtDTO);

				for (AddReasonCdnSympElmtDTO symptomElmt : symptomElmtList) {
					String factorName = symptomElmt.getFactorName();
					//推理规则条件
					WmkgReasonCdn wmkgReasonCdn = new WmkgReasonCdn();
					BeanUtil.copyProperties(symptomElmt, wmkgReasonCdn);
					wmkgReasonCdn.setFactorType(factorName);
					wmkgReasonCdn.setFactorSort(factorSort);
					wmkgReasonCdn.setReasonId(wmkgReasonDefine.getReasonId());
					wmkgReasonCdn.setGroupId(wmkgReasonGroup.getGroupId());
					wrapWmkgReasonCdn(factorName, wmkgReasonCdn, factorCate);
					this.saveOrUpdate(wmkgReasonCdn);

					String cdnId = wmkgReasonCdn.getCdnId();

					//推理规则条件-关联症状元素
					WmkgReasonCdnSympElmt wmkgReasonCdnSympElmt = new WmkgReasonCdnSympElmt();
					BeanUtil.copyProperties(symptomElmt, wmkgReasonCdnSympElmt);
					wmkgReasonCdnSympElmt.setSymptomCode(symptomCode);
					wmkgReasonCdnSympElmt.setSymptomName(symptomName);
					wmkgReasonCdnSympElmt.setFactorType(factorName);
					wmkgReasonCdnSympElmt.setFactorSort(factorSort);
					wmkgReasonCdnSympElmt.setCdnId(cdnId);
					wmkgReasonCdnSympElmtService.saveOrUpdate(wmkgReasonCdnSympElmt);

					//推理规则条件-关联症状
					WmkgReasonCdnSymptom wmkgReasonCdnSymptom = new WmkgReasonCdnSymptom();
					wmkgReasonCdnSymptom.setSymptomCode(symptomCode);
					wmkgReasonCdnSymptom.setSymptomName(symptomName);
					wmkgReasonCdnSymptom.setSymptomMain(symptomMain);
					wmkgReasonCdnSymptom.setFactorWeight(factorWeight);
					wmkgReasonCdnSymptom.setCdnId(cdnId);
					wmkgReasonCdnSymptomService.saveOrUpdate(wmkgReasonCdnSymptom);
				}
			}
		}

		return wmkgReasonDefine.getReasonId();
	}


	public WmkgReasonDefine saveReasonDefine(AddReasonDefineDTO reasonDefine) {
		WmkgReasonDefine wmkgReasonDefine = new WmkgReasonDefine();
		String reasonId = reasonDefine.getReasonId();
		if (StrUtil.isNotBlank(reasonId)) {
			WmkgReasonDefine reasonDef = wmkgReasonDefineService.getById(reasonId);
			if (Objects.isNull(reasonDef)) {
				throw BusinessException.create("方案不存在");
			}
			if (1 == reasonDef.getReasonStatus()) {
				throw BusinessException.create("该方案已上线，不能修改");
			}
		} else {
			String diseaseCode = reasonDefine.getDiseaseCode();
			if (StrUtil.isNotBlank(diseaseCode)) {
				WmkgReasonDefine reasonDef = wmkgReasonDefineService.getOne(Wrappers.<WmkgReasonDefine>lambdaQuery()
						.eq(WmkgReasonDefine::getDiseaseCode, diseaseCode)
						.eq(WmkgReasonDefine::getReasonName, reasonDefine.getReasonName()));
				if (Objects.nonNull(reasonDef)) {
					throw BusinessException.create("方案名在当前疾病中已存在！");
				}
			}
		}
		BeanUtil.copyProperties(reasonDefine, wmkgReasonDefine);
		wmkgReasonDefine.setReasonStatus(0);
		wmkgReasonDefineService.saveOrUpdate(wmkgReasonDefine);
		return wmkgReasonDefine;
	}


	public void wrapWmkgReasonCdn(String factorName, WmkgReasonCdn wmkgReasonCdn, String parentName) {
		LambdaQueryWrapper<WmkgReasonFactor> eq = Wrappers.<WmkgReasonFactor>lambdaQuery()
				.eq(WmkgReasonFactor::getFactorName, factorName);
		if (StrUtil.isNotBlank(parentName)) {
			eq.eq(WmkgReasonFactor::getParentFactor, parentName);
		}
		WmkgReasonFactor reasonFactor = wmkgReasonFactorService.getOne(eq);
		if (Objects.nonNull(reasonFactor)) {
			wmkgReasonCdn.setFactorDefine(reasonFactor.getFactorDefine());
			wmkgReasonCdn.setFactorDataType(reasonFactor.getFactorDataType());
			wmkgReasonCdn.setFactorUnit(reasonFactor.getFactorUnit());
		}
	}

	@Override
	public AuReasonDefineDTO<AddReasonCdnSymptomDTO> getReasonCdnSymp(String reasonId) {

		AuReasonDefineDTO<AddReasonCdnSymptomDTO> vo = new AuReasonDefineDTO<>();
		WmkgReasonDefine reasonDefine = wmkgReasonDefineService.getById(reasonId);
		if (Objects.isNull(reasonDefine)) {
			log.info("方案不存在:{}", reasonId);
			return null;
		}

		//获取方案详情
		AddReasonDefineDTO reasonDefine1 = BeanConvertUtil.convertBean(reasonDefine, AddReasonDefineDTO.class);
		vo.setReasonDefine(reasonDefine1);

		WmkgReasonCategory reasonCategory = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonId)
				.eq(WmkgReasonCategory::getFactorCate, "症状"));
		if (Objects.isNull(reasonCategory)) {
			log.info("推理疾病业务分组不存在：{}", reasonId);
			return vo;
		}

		AddReasonCategory<AddReasonCdnSymptomDTO> addReasonCategory = new AddReasonCategory<>();
		BeanUtil.copyProperties(reasonCategory, addReasonCategory);

		List<AddReasonGroup<AddReasonCdnSymptomDTO>> reasonGroups = new ArrayList<>();
		List<WmkgReasonGroup> wmkgReasonGroups = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonId)
				.eq(WmkgReasonGroup::getFactorCate, "症状")
				.orderByAsc(WmkgReasonGroup::getGroupSort));
		List<String> groupIds = wmkgReasonGroups.stream().map(WmkgReasonGroup::getGroupId).collect(Collectors.toList());
		Map<String, WmkgReasonGroup> reasonGroupsMap = wmkgReasonGroups.stream().collect(Collectors.toMap(WmkgReasonGroup::getGroupId, o -> o));

		List<AddReasonCdnSympElmtDTO> reasonCdnSymptom = wmkgReasonCdnMapper.getReasonCdnSymptom(groupIds);
		if (CollectionUtil.isEmpty(reasonCdnSymptom)) {
			return vo;
		}

		List<String> cdnIds = reasonCdnSymptom.stream().map(AddReasonCdnSympElmtDTO::getCdnId).distinct().collect(Collectors.toList());
		List<WmkgReasonCdnSymptom> symptoms = wmkgReasonCdnSymptomService.listByIds(cdnIds);
		Map<String, WmkgReasonCdnSymptom> symptomMap = symptoms.stream().collect(Collectors.toMap(WmkgReasonCdnSymptom::getCdnId, o -> o));

		Map<String, List<AddReasonCdnSympElmtDTO>> reasonCdnSymptomGroup = reasonCdnSymptom.stream().collect(Collectors.groupingBy(AddReasonCdnSympElmtDTO::getGroupId));
		Set<Map.Entry<String, List<AddReasonCdnSympElmtDTO>>> entries = reasonCdnSymptomGroup.entrySet();
		for (Map.Entry<String, List<AddReasonCdnSympElmtDTO>> entry : entries) {
			String groupId = entry.getKey();
			WmkgReasonGroup wmkgReasonGroup = reasonGroupsMap.get(groupId);
			AddReasonGroup<AddReasonCdnSymptomDTO> addReasonGroup = new AddReasonGroup<>();
			BeanUtil.copyProperties(wmkgReasonGroup, addReasonGroup);

			List<AddReasonCdnSympElmtDTO> reasonCdns = entry.getValue();
			if (CollectionUtil.isEmpty(reasonCdns)) {
				continue;
			}

			Map<String, List<AddReasonCdnSympElmtDTO>> symptomGroup = reasonCdns.stream()
					.collect(Collectors.groupingBy(AddReasonCdnSympElmtDTO::getSymptomCode, LinkedHashMap::new, Collectors.toList()));
			List<AddReasonCdnSymptomDTO> addReasonCdnSymptomDTOS = new ArrayList<>();
			for (Map.Entry<String, List<AddReasonCdnSympElmtDTO>> symptomEntry : symptomGroup.entrySet()) {
				List<AddReasonCdnSympElmtDTO> symptomElmtList = symptomEntry.getValue();
				List<AddReasonCdnSympElmtDTO> cdnSympElemtList = BeanConvertUtil.convertListBean(symptomElmtList, AddReasonCdnSympElmtDTO.class);
				AddReasonCdnSympElmtDTO sympElmtDTO = symptomElmtList.get(0);
				AddReasonCdnSymptomDTO addReasonCdnSymptomDTO = new AddReasonCdnSymptomDTO();
				WmkgReasonCdnSymptom wmkgReasonCdnSymptom = symptomMap.get(sympElmtDTO.getCdnId());
				BeanUtil.copyProperties(wmkgReasonCdnSymptom, addReasonCdnSymptomDTO);
				addReasonCdnSymptomDTO.setSymptomElmtList(cdnSympElemtList);
				addReasonCdnSymptomDTOS.add(addReasonCdnSymptomDTO);
			}
			addReasonGroup.setReasonCdns(addReasonCdnSymptomDTOS);
			reasonGroups.add(addReasonGroup);
		}
		addReasonCategory.setReasonGroups(reasonGroups);
		vo.setReasonCategory(addReasonCategory);
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveReasonCdnMulti(AuReasonDefineDTO<AddReasonCdnFrequentDTO> dto) {
		//保存或更新方案表
		AddReasonDefineDTO reasonDefine = dto.getReasonDefine();
		WmkgReasonDefine wmkgReasonDefine = saveReasonDefine(reasonDefine);

		AddReasonCategory<AddReasonCdnFrequentDTO> reasonCategory = dto.getReasonCategory();
		String factorCate = reasonCategory.getFactorCate();

		//新增规则分类
		WmkgReasonCategory one = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate()));
		if (Objects.isNull(one)) {
			WmkgReasonCategory wmkgReasonCategory = new WmkgReasonCategory();
			BeanUtil.copyProperties(reasonCategory, wmkgReasonCategory);
			wmkgReasonCategory.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonCategoryService.save(wmkgReasonCategory);
		} else {
			//更新比例值
			wmkgReasonCategoryService.update(Wrappers.<WmkgReasonCategory>lambdaUpdate()
					.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
					.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate())
					.set(WmkgReasonCategory::getFactorRatio, reasonCategory.getFactorRatio()));
		}

		//清除旧数据
		List<WmkgReasonGroup> groupList = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonGroup::getFactorCate, factorCate));
		List<String> groupIds = groupList.stream().map(WmkgReasonGroup::getGroupId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(groupIds)) {
			wmkgReasonGroupService.removeByIds(groupIds);
			List<WmkgReasonCdn> list = this.list(Wrappers.<WmkgReasonCdn>lambdaQuery()
					.in(WmkgReasonCdn::getGroupId, groupIds));
			if (CollUtil.isNotEmpty(list)) {
				List<String> cdnIds = list.stream().map(WmkgReasonCdn::getCdnId).collect(Collectors.toList());
				this.remove(Wrappers.<WmkgReasonCdn>lambdaQuery().in(WmkgReasonCdn::getCdnId, cdnIds));
				wmkgReasonCdnFrequentService.remove(Wrappers.<WmkgReasonCdnFrequent>lambdaQuery().in(WmkgReasonCdnFrequent::getCdnId, cdnIds));
			}
		}

		//保存或更新规则组
		List<AddReasonGroup<AddReasonCdnFrequentDTO>> reasonGroups = reasonCategory.getReasonGroups();
		for (AddReasonGroup<AddReasonCdnFrequentDTO> reasonGroup : reasonGroups) {
			WmkgReasonGroup wmkgReasonGroup = new WmkgReasonGroup();
			BeanUtil.copyProperties(reasonGroup, wmkgReasonGroup);
			wmkgReasonGroup.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonGroup.setFactorCate(factorCate);
			wmkgReasonGroupService.saveOrUpdate(wmkgReasonGroup);

			//规则组内条件
			List<AddReasonCdnFrequentDTO> reasonCdns = reasonGroup.getReasonCdns();
			for (AddReasonCdnFrequentDTO reasonCdn : reasonCdns) {
				String factorName = reasonCdn.getFactorName();
				if ("年龄".equals(factorName)){
					String factorValue = reasonCdn.getFactorValue();
					String[] values =  factorValue.split(";");
					for (String value : values){
						boolean b = validateAge(value);
						if (!b){
							throw BusinessException.create("年龄格式错误,运算符、单位可能不在枚举范围内");
						}
					}
				}

				//推理规则条件
				WmkgReasonCdn wmkgReasonCdn = new WmkgReasonCdn();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdn);
				wmkgReasonCdn.setFactorType(factorName);
				wmkgReasonCdn.setReasonId(wmkgReasonDefine.getReasonId());
				wmkgReasonCdn.setGroupId(wmkgReasonGroup.getGroupId());
				wrapWmkgReasonCdn(factorName, wmkgReasonCdn, factorCate);
				this.saveOrUpdate(wmkgReasonCdn);
				String cdnId = wmkgReasonCdn.getCdnId();

				//推理规则条件-多发
				WmkgReasonCdnFrequent wmkgReasonCdnFrequent = new WmkgReasonCdnFrequent();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnFrequent);
				wmkgReasonCdnFrequent.setCdnId(cdnId);
				wmkgReasonCdnFrequent.setFactorType(factorName);
				wmkgReasonCdnFrequentService.saveOrUpdate(wmkgReasonCdnFrequent);

			}
		}

		return wmkgReasonDefine.getReasonId();
	}

	@Override
	public AuReasonDefineDTO<AddReasonCdnFrequentDTO> getReasonCdnMulti(String reasonId, String factorCate) {

		AuReasonDefineDTO<AddReasonCdnFrequentDTO> vo = new AuReasonDefineDTO<>();
		WmkgReasonDefine reasonDefine = wmkgReasonDefineService.getById(reasonId);
		if (Objects.isNull(reasonDefine)) {
			log.info("方案不存在:{}", reasonId);
			return null;
		}

		//获取方案详情
		AddReasonDefineDTO reasonDefine1 = BeanConvertUtil.convertBean(reasonDefine, AddReasonDefineDTO.class);
		vo.setReasonDefine(reasonDefine1);

		WmkgReasonCategory reasonCategory = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonId)
				.eq(WmkgReasonCategory::getFactorCate, factorCate));
		if (Objects.isNull(reasonCategory)) {
			log.info("推理疾病业务分组不存在：{}", reasonId);
			return vo;
		}

		AddReasonCategory<AddReasonCdnFrequentDTO> addReasonCategory = new AddReasonCategory<>();
		BeanUtil.copyProperties(reasonCategory, addReasonCategory);

		List<AddReasonGroup<AddReasonCdnFrequentDTO>> reasonGroups = new ArrayList<>();
		List<WmkgReasonGroup> wmkgReasonGroups = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonId)
				.eq(WmkgReasonGroup::getFactorCate, factorCate));

		List<String> groupIds = wmkgReasonGroups.stream().map(WmkgReasonGroup::getGroupId).collect(Collectors.toList());
		List<AddReasonCdnFrequentDTO> reasonCdnFrequent = wmkgReasonCdnMapper.getReasonCdnFrequent(groupIds);
		Map<String, List<AddReasonCdnFrequentDTO>> reasonCdnFrequentGroup = reasonCdnFrequent.stream().collect(Collectors.groupingBy(AddReasonCdnFrequentDTO::getGroupId));

		for (WmkgReasonGroup wmkgReasonGroup : wmkgReasonGroups) {
			AddReasonGroup<AddReasonCdnFrequentDTO> addReasonGroup = new AddReasonGroup<>();
			BeanUtil.copyProperties(wmkgReasonGroup, addReasonGroup);
			addReasonGroup.setReasonCdns(reasonCdnFrequentGroup.get(wmkgReasonGroup.getGroupId()));
			reasonGroups.add(addReasonGroup);
		}
		addReasonCategory.setReasonGroups(reasonGroups);
		vo.setReasonCategory(addReasonCategory);
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveReasonCdnPhysical(AuReasonDefineDTO<AddReasonCdnFrequentDTO> dto) {
		//保存或更新方案表
		AddReasonDefineDTO reasonDefine = dto.getReasonDefine();
		WmkgReasonDefine wmkgReasonDefine = saveReasonDefine(reasonDefine);

		AddReasonCategory<AddReasonCdnFrequentDTO> reasonCategory = dto.getReasonCategory();
		String factorCate = reasonCategory.getFactorCate();

		//新增规则分类
		WmkgReasonCategory one = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate()));
		if (Objects.isNull(one)) {
			WmkgReasonCategory wmkgReasonCategory = new WmkgReasonCategory();
			BeanUtil.copyProperties(reasonCategory, wmkgReasonCategory);
			wmkgReasonCategory.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonCategoryService.save(wmkgReasonCategory);
		} else {
			//更新比例值
			wmkgReasonCategoryService.update(Wrappers.<WmkgReasonCategory>lambdaUpdate()
					.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
					.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate())
					.set(WmkgReasonCategory::getFactorRatio, reasonCategory.getFactorRatio()));
		}

		//清除旧数据
		List<WmkgReasonGroup> groupList = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonGroup::getFactorCate, factorCate));
		List<String> groupIds = groupList.stream().map(WmkgReasonGroup::getGroupId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(groupIds)) {
			wmkgReasonGroupService.removeByIds(groupIds);
			List<WmkgReasonCdn> list = this.list(Wrappers.<WmkgReasonCdn>lambdaQuery()
					.in(WmkgReasonCdn::getGroupId, groupIds));
			if (CollUtil.isNotEmpty(list)) {
				List<String> cdnIds = list.stream().map(WmkgReasonCdn::getCdnId).collect(Collectors.toList());
				this.remove(Wrappers.<WmkgReasonCdn>lambdaQuery().in(WmkgReasonCdn::getCdnId, cdnIds));
				wmkgReasonCdnPhysicalService.remove(Wrappers.<WmkgReasonCdnPhysical>lambdaQuery().in(WmkgReasonCdnPhysical::getCdnId, cdnIds));
				wmkgReasonCdnExpService.remove(Wrappers.<WmkgReasonCdnExp>lambdaQuery().in(WmkgReasonCdnExp::getCdnId, cdnIds));
			}
		}

		//保存或更新规则组
		List<AddReasonGroup<AddReasonCdnFrequentDTO>> reasonGroups = reasonCategory.getReasonGroups();
		for (AddReasonGroup<AddReasonCdnFrequentDTO> reasonGroup : reasonGroups) {
			WmkgReasonGroup wmkgReasonGroup = new WmkgReasonGroup();
			BeanUtil.copyProperties(reasonGroup, wmkgReasonGroup);
			wmkgReasonGroup.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonGroup.setFactorCate(factorCate);
			wmkgReasonGroupService.saveOrUpdate(wmkgReasonGroup);

			//规则组内条件
			List<AddReasonCdnFrequentDTO> reasonCdns = reasonGroup.getReasonCdns();
			for (AddReasonCdnFrequentDTO reasonCdn : reasonCdns) {
				String factorName = reasonCdn.getFactorName();
				//推理规则条件
				WmkgReasonCdn wmkgReasonCdn = new WmkgReasonCdn();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdn);
				wmkgReasonCdn.setFactorType(factorName);
				wmkgReasonCdn.setReasonId(wmkgReasonDefine.getReasonId());
				wmkgReasonCdn.setGroupId(wmkgReasonGroup.getGroupId());
				wrapWmkgReasonCdn(factorName, wmkgReasonCdn, factorCate);
				this.saveOrUpdate(wmkgReasonCdn);

				String cdnId = wmkgReasonCdn.getCdnId();

				//推理规则条件-体格检查
				WmkgReasonCdnPhysical wmkgReasonCdnPhysical = new WmkgReasonCdnPhysical();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnPhysical);
				wmkgReasonCdnPhysical.setFactorType(factorName);
				wmkgReasonCdnPhysical.setCdnId(cdnId);
				wmkgReasonCdnPhysicalService.saveOrUpdate(wmkgReasonCdnPhysical);

				if ("定量".equals(wmkgReasonCdn.getFactorDefine())) {
					if (StrUtil.isBlank(reasonCdn.getConfigValue())
							|| StrUtil.isBlank(reasonCdn.getTargetValue())
							|| StrUtil.isBlank(reasonCdn.getOp())) {
						throw BusinessException.create("条件表达式需要传递完整条件（配置值、目标值、操作符）");
					}
					//推理表达式
					WmkgReasonCdnExp wmkgReasonCdnExp = new WmkgReasonCdnExp();
					BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnExp);
					wmkgReasonCdnExp.setCdnId(cdnId);
					wmkgReasonCdnExpService.saveOrUpdate(wmkgReasonCdnExp);
				}
			}
		}

		return wmkgReasonDefine.getReasonId();
	}

	@Override
	public AuReasonDefineDTO<AddReasonCdnFrequentDTO> getReasonCdnPhysical(String reasonId, String factorCate) {

		AuReasonDefineDTO<AddReasonCdnFrequentDTO> vo = new AuReasonDefineDTO<>();
		WmkgReasonDefine reasonDefine = wmkgReasonDefineService.getById(reasonId);
		if (Objects.isNull(reasonDefine)) {
			log.info("方案不存在:{}", reasonId);
			return null;
		}

		//获取方案详情
		AddReasonDefineDTO reasonDefine1 = BeanConvertUtil.convertBean(reasonDefine, AddReasonDefineDTO.class);
		vo.setReasonDefine(reasonDefine1);

		WmkgReasonCategory reasonCategory = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonId)
				.eq(WmkgReasonCategory::getFactorCate, factorCate));
		if (Objects.isNull(reasonCategory)) {
			log.info("推理疾病业务分组不存在：{}", reasonId);
			return vo;
		}

		AddReasonCategory<AddReasonCdnFrequentDTO> addReasonCategory = new AddReasonCategory<>();
		BeanUtil.copyProperties(reasonCategory, addReasonCategory);

		List<AddReasonGroup<AddReasonCdnFrequentDTO>> reasonGroups = new ArrayList<>();
		List<WmkgReasonGroup> wmkgReasonGroups = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonId)
				.eq(WmkgReasonGroup::getFactorCate, factorCate));

		List<String> groupIds = wmkgReasonGroups.stream().map(WmkgReasonGroup::getGroupId).collect(Collectors.toList());
		List<AddReasonCdnFrequentDTO> reasonCdnFrequent = wmkgReasonCdnMapper.getReasonCdnPhysical(groupIds);
		Map<String, List<AddReasonCdnFrequentDTO>> reasonCdnFrequentGroup = reasonCdnFrequent.stream().collect(Collectors.groupingBy(AddReasonCdnFrequentDTO::getGroupId));

		for (WmkgReasonGroup wmkgReasonGroup : wmkgReasonGroups) {
			AddReasonGroup<AddReasonCdnFrequentDTO> addReasonGroup = new AddReasonGroup<>();
			BeanUtil.copyProperties(wmkgReasonGroup, addReasonGroup);
			addReasonGroup.setReasonCdns(reasonCdnFrequentGroup.get(wmkgReasonGroup.getGroupId()));
			reasonGroups.add(addReasonGroup);
		}
		addReasonCategory.setReasonGroups(reasonGroups);
		vo.setReasonCategory(addReasonCategory);
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveReasonCdnExam(AuReasonDefineDTO<AddReasonCdnExamDTO> dto) {
		//保存或更新方案表
		AddReasonDefineDTO reasonDefine = dto.getReasonDefine();
		WmkgReasonDefine wmkgReasonDefine = saveReasonDefine(reasonDefine);

		AddReasonCategory<AddReasonCdnExamDTO> reasonCategory = dto.getReasonCategory();
		String factorCate = reasonCategory.getFactorCate();

		//新增规则分类
		WmkgReasonCategory one = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate()));
		if (Objects.isNull(one)) {
			WmkgReasonCategory wmkgReasonCategory = new WmkgReasonCategory();
			BeanUtil.copyProperties(reasonCategory, wmkgReasonCategory);
			wmkgReasonCategory.setFactorCate(factorCate);
			wmkgReasonCategory.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonCategoryService.save(wmkgReasonCategory);
		} else {
			//更新比例值
			wmkgReasonCategoryService.update(Wrappers.<WmkgReasonCategory>lambdaUpdate()
					.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
					.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate())
					.set(WmkgReasonCategory::getFactorRatio, reasonCategory.getFactorRatio()));
		}

		//清除旧数据
		List<WmkgReasonGroup> groupList = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonGroup::getFactorCate, factorCate));
		List<String> groupIds = groupList.stream().map(WmkgReasonGroup::getGroupId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(groupIds)) {
			wmkgReasonGroupService.removeByIds(groupIds);
			List<WmkgReasonCdn> list = this.list(Wrappers.<WmkgReasonCdn>lambdaQuery()
					.in(WmkgReasonCdn::getGroupId, groupIds));
			if (CollUtil.isNotEmpty(list)) {
				List<String> cdnIds = list.stream().map(WmkgReasonCdn::getCdnId).collect(Collectors.toList());
				this.remove(Wrappers.<WmkgReasonCdn>lambdaQuery().in(WmkgReasonCdn::getCdnId, cdnIds));
				wmkgReasonCdnExamService.remove(Wrappers.<WmkgReasonCdnExam>lambdaQuery().in(WmkgReasonCdnExam::getCdnId, cdnIds));
				wmkgReasonCdnExpService.remove(Wrappers.<WmkgReasonCdnExp>lambdaQuery().in(WmkgReasonCdnExp::getCdnId, cdnIds));
			}
		}

		//保存或更新规则组
		List<AddReasonGroup<AddReasonCdnExamDTO>> reasonGroups = reasonCategory.getReasonGroups();
		for (AddReasonGroup<AddReasonCdnExamDTO> reasonGroup : reasonGroups) {
			WmkgReasonGroup wmkgReasonGroup = new WmkgReasonGroup();
			BeanUtil.copyProperties(reasonGroup, wmkgReasonGroup);
			wmkgReasonGroup.setFactorCate(factorCate);
			wmkgReasonGroup.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonGroupService.saveOrUpdate(wmkgReasonGroup);

			//规则组内条件
			List<AddReasonCdnExamDTO> reasonCdns = reasonGroup.getReasonCdns();
			for (AddReasonCdnExamDTO reasonCdn : reasonCdns) {
				//推理规则条件
				WmkgReasonCdn wmkgReasonCdn = new WmkgReasonCdn();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdn);
				wmkgReasonCdn.setReasonId(wmkgReasonDefine.getReasonId());
				wmkgReasonCdn.setGroupId(wmkgReasonGroup.getGroupId());
				wrapWmkgReasonCdn(reasonCdn.getFactorType(), wmkgReasonCdn, factorCate);
				this.saveOrUpdate(wmkgReasonCdn);
				String cdnId = wmkgReasonCdn.getCdnId();

				//推理规则条件-检查项目
				WmkgReasonCdnExam wmkgReasonCdnExam = new WmkgReasonCdnExam();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnExam);
				wmkgReasonCdnExam.setCdnId(cdnId);
				wmkgReasonCdnExamService.saveOrUpdate(wmkgReasonCdnExam);

				if ("异常指标".equals(reasonCdn.getFactorType())) {
					//推理表达式
					if (StrUtil.isBlank(reasonCdn.getConfigValue())
							|| StrUtil.isBlank(reasonCdn.getTargetValue())
							|| StrUtil.isBlank(reasonCdn.getOp())) {
						throw BusinessException.create("条件表达式需要传递完整条件（配置值、目标值、操作符）");
					}
					WmkgReasonCdnExp wmkgReasonCdnExp = new WmkgReasonCdnExp();
					BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnExp);
					wmkgReasonCdnExp.setCdnId(cdnId);
					wmkgReasonCdnExpService.saveOrUpdate(wmkgReasonCdnExp);
				}
			}
		}

		return wmkgReasonDefine.getReasonId();
	}

	@Override
	public AuReasonDefineDTO<AddReasonCdnExamDTO> getReasonCdnExam(String reasonId, String factorCate) {

		AuReasonDefineDTO<AddReasonCdnExamDTO> vo = new AuReasonDefineDTO<>();
		WmkgReasonDefine reasonDefine = wmkgReasonDefineService.getById(reasonId);
		if (Objects.isNull(reasonDefine)) {
			log.info("方案不存在:{}", reasonId);
			return null;
		}

		//获取方案详情
		AddReasonDefineDTO reasonDefine1 = BeanConvertUtil.convertBean(reasonDefine, AddReasonDefineDTO.class);
		vo.setReasonDefine(reasonDefine1);

		WmkgReasonCategory reasonCategory = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonId)
				.eq(WmkgReasonCategory::getFactorCate, factorCate));
		if (Objects.isNull(reasonCategory)) {
			log.info("推理疾病业务分组不存在：{}", reasonId);
			return vo;
		}

		AddReasonCategory<AddReasonCdnExamDTO> addReasonCategory = new AddReasonCategory<>();
		BeanUtil.copyProperties(reasonCategory, addReasonCategory);

		List<AddReasonGroup<AddReasonCdnExamDTO>> reasonGroups = new ArrayList<>();
		List<WmkgReasonGroup> wmkgReasonGroups = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonId)
				.eq(WmkgReasonGroup::getFactorCate, factorCate));

		List<String> groupIds = wmkgReasonGroups.stream().map(WmkgReasonGroup::getGroupId).collect(Collectors.toList());
		List<AddReasonCdnExamDTO> reasonCdnExams = wmkgReasonCdnMapper.getReasonCdnExam(groupIds);
		Map<String, List<AddReasonCdnExamDTO>> reasonCdnExamsGroup = reasonCdnExams.stream().collect(Collectors.groupingBy(AddReasonCdnExamDTO::getGroupId));

		for (WmkgReasonGroup wmkgReasonGroup : wmkgReasonGroups) {
			AddReasonGroup<AddReasonCdnExamDTO> addReasonGroup = new AddReasonGroup<>();
			BeanUtil.copyProperties(wmkgReasonGroup, addReasonGroup);
			addReasonGroup.setReasonCdns(reasonCdnExamsGroup.get(wmkgReasonGroup.getGroupId()));
			reasonGroups.add(addReasonGroup);
		}
		addReasonCategory.setReasonGroups(reasonGroups);
		vo.setReasonCategory(addReasonCategory);
		return vo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveReasonCdnLabtest(AuReasonDefineDTO<AddReasonCdnLabtestDTO> dto) {
		//保存或更新方案表
		AddReasonDefineDTO reasonDefine = dto.getReasonDefine();
		WmkgReasonDefine wmkgReasonDefine = saveReasonDefine(reasonDefine);

		AddReasonCategory<AddReasonCdnLabtestDTO> reasonCategory = dto.getReasonCategory();
		String factorCate = reasonCategory.getFactorCate();

		//新增规则分类
		WmkgReasonCategory one = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate()));
		if (Objects.isNull(one)) {
			WmkgReasonCategory wmkgReasonCategory = new WmkgReasonCategory();
			BeanUtil.copyProperties(reasonCategory, wmkgReasonCategory);
			wmkgReasonCategory.setFactorCate(factorCate);
			wmkgReasonCategory.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonCategoryService.save(wmkgReasonCategory);
		} else {
			//更新比例值
			wmkgReasonCategoryService.update(Wrappers.<WmkgReasonCategory>lambdaUpdate()
					.eq(WmkgReasonCategory::getReasonId, reasonDefine.getReasonId())
					.eq(WmkgReasonCategory::getFactorCate, reasonCategory.getFactorCate())
					.set(WmkgReasonCategory::getFactorRatio, reasonCategory.getFactorRatio()));
		}

		//清除旧数据
		List<WmkgReasonGroup> groupList = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonDefine.getReasonId())
				.eq(WmkgReasonGroup::getFactorCate, factorCate));
		List<String> groupIds = groupList.stream().map(WmkgReasonGroup::getGroupId).filter(StrUtil::isNotBlank).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(groupIds)) {
			wmkgReasonGroupService.removeByIds(groupIds);
			List<WmkgReasonCdn> list = this.list(Wrappers.<WmkgReasonCdn>lambdaQuery()
					.in(WmkgReasonCdn::getGroupId, groupIds));
			if (CollUtil.isNotEmpty(list)) {
				List<String> cdnIds = list.stream().map(WmkgReasonCdn::getCdnId).collect(Collectors.toList());
				this.remove(Wrappers.<WmkgReasonCdn>lambdaQuery().in(WmkgReasonCdn::getCdnId, cdnIds));
				wmkgReasonCdnLabtestService.remove(Wrappers.<WmkgReasonCdnLabtest>lambdaQuery().in(WmkgReasonCdnLabtest::getCdnId, cdnIds));
				wmkgReasonCdnExpService.remove(Wrappers.<WmkgReasonCdnExp>lambdaQuery().in(WmkgReasonCdnExp::getCdnId, cdnIds));
			}
		}

		//保存或更新规则组
		List<AddReasonGroup<AddReasonCdnLabtestDTO>> reasonGroups = reasonCategory.getReasonGroups();
		for (AddReasonGroup<AddReasonCdnLabtestDTO> reasonGroup : reasonGroups) {
			WmkgReasonGroup wmkgReasonGroup = new WmkgReasonGroup();
			BeanUtil.copyProperties(reasonGroup, wmkgReasonGroup);
			wmkgReasonGroup.setFactorCate(factorCate);
			wmkgReasonGroup.setReasonId(wmkgReasonDefine.getReasonId());
			wmkgReasonGroupService.saveOrUpdate(wmkgReasonGroup);

			//规则组内条件
			List<AddReasonCdnLabtestDTO> reasonCdns = reasonGroup.getReasonCdns();
			for (AddReasonCdnLabtestDTO reasonCdn : reasonCdns) {

				if (StrUtil.isBlank(reasonCdn.getConfigValue())
						|| StrUtil.isBlank(reasonCdn.getTargetValue())
						|| StrUtil.isBlank(reasonCdn.getOp())) {
					throw BusinessException.create("条件表达式需要传递完整条件（配置值、目标值、操作符）");
				}

				//推理规则条件
				WmkgReasonCdn wmkgReasonCdn = new WmkgReasonCdn();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdn);
				wmkgReasonCdn.setReasonId(wmkgReasonDefine.getReasonId());
				wmkgReasonCdn.setGroupId(wmkgReasonGroup.getGroupId());
				wrapWmkgReasonCdn(wmkgReasonCdn.getFactorType(), wmkgReasonCdn, factorCate);
				this.saveOrUpdate(wmkgReasonCdn);
				String cdnId = wmkgReasonCdn.getCdnId();

				//推理规则条件-检验项目
				WmkgReasonCdnLabtest wmkgReasonCdnLabtest = new WmkgReasonCdnLabtest();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnLabtest);
				wmkgReasonCdnLabtest.setCdnId(cdnId);
				wmkgReasonCdnLabtestService.saveOrUpdate(wmkgReasonCdnLabtest);

				//推理表达式
				WmkgReasonCdnExp wmkgReasonCdnExp = new WmkgReasonCdnExp();
				BeanUtil.copyProperties(reasonCdn, wmkgReasonCdnExp);
				wmkgReasonCdnExp.setCdnId(cdnId);
				wmkgReasonCdnExpService.saveOrUpdate(wmkgReasonCdnExp);
			}
		}

		return wmkgReasonDefine.getReasonId();
	}

	@Override
	public AuReasonDefineDTO<AddReasonCdnLabtestDTO> getReasonCdnLabtest(String reasonId, String factorCate) {

		AuReasonDefineDTO<AddReasonCdnLabtestDTO> vo = new AuReasonDefineDTO<>();
		WmkgReasonDefine reasonDefine = wmkgReasonDefineService.getById(reasonId);
		if (Objects.isNull(reasonDefine)) {
			log.info("方案不存在:{}", reasonId);
			return null;
		}

		//获取方案详情
		AddReasonDefineDTO reasonDefine1 = BeanConvertUtil.convertBean(reasonDefine, AddReasonDefineDTO.class);
		vo.setReasonDefine(reasonDefine1);

		WmkgReasonCategory reasonCategory = wmkgReasonCategoryService.getOne(Wrappers.<WmkgReasonCategory>lambdaQuery()
				.eq(WmkgReasonCategory::getReasonId, reasonId)
				.eq(WmkgReasonCategory::getFactorCate, factorCate));
		if (Objects.isNull(reasonCategory)) {
			log.info("推理疾病业务分组不存在：{}", reasonId);
			return vo;
		}

		AddReasonCategory<AddReasonCdnLabtestDTO> addReasonCategory = new AddReasonCategory<>();
		BeanUtil.copyProperties(reasonCategory, addReasonCategory);

		List<AddReasonGroup<AddReasonCdnLabtestDTO>> reasonGroups = new ArrayList<>();
		List<WmkgReasonGroup> wmkgReasonGroups = wmkgReasonGroupService.list(Wrappers.<WmkgReasonGroup>lambdaQuery()
				.eq(WmkgReasonGroup::getReasonId, reasonId)
				.eq(WmkgReasonGroup::getFactorCate, factorCate));

		List<String> groupIds = wmkgReasonGroups.stream().map(WmkgReasonGroup::getGroupId).collect(Collectors.toList());
		List<AddReasonCdnLabtestDTO> reasonCdnLabtests = wmkgReasonCdnMapper.getReasonCdnLabtest(groupIds);
		Map<String, List<AddReasonCdnLabtestDTO>> reasonCdnExamsGroup = reasonCdnLabtests.stream().collect(Collectors.groupingBy(AddReasonCdnLabtestDTO::getGroupId));

		for (WmkgReasonGroup wmkgReasonGroup : wmkgReasonGroups) {
			AddReasonGroup<AddReasonCdnLabtestDTO> addReasonGroup = new AddReasonGroup<>();
			BeanUtil.copyProperties(wmkgReasonGroup, addReasonGroup);
			addReasonGroup.setReasonCdns(reasonCdnExamsGroup.get(wmkgReasonGroup.getGroupId()));
			reasonGroups.add(addReasonGroup);
		}
		addReasonCategory.setReasonGroups(reasonGroups);
		vo.setReasonCategory(addReasonCategory);
		return vo;
	}


	/**
	 * 验证年龄是否满足给定的条件。
	 *
	 * @param condition 条件字符串，例如 ">=2岁" 或 "青年"
	 * @return 如果年龄满足条件返回 true，否则返回 false
	 */
	public boolean validateAge(String condition) {
		if (isChineseStart(condition)) {
			return isExempt(condition);
		} else {
			return validateWithOperator(condition);
		}
	}

	/**
	 * 判断条件字符串是否以中文开头。
	 *
	 * @param condition 条件字符串
	 * @return 如果以中文开头返回 true，否则返回 false
	 */
	private boolean isChineseStart(String condition) {
		return Pattern.matches("^[\u4e00-\u9fa5]+", condition);
	}

	/**
	 * 根据运算符验证年龄。
	 *
	 * @param condition 条件字符串
	 * @return 如果年龄满足条件返回 true，否则返回 false
	 */
	private boolean validateWithOperator(String condition) {
		Pattern pattern = Pattern.compile("(>=|<=|=|>|<)(\\d+)(岁|天|年|周|月)");
		Matcher matcher = pattern.matcher(condition);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}


	/**
	 * 判断条件是否属于不需要验证的情况。
	 *
	 * @param condition 条件字符串
	 * @return 如果属于不需要验证的情况返回 true，否则返回 false
	 */
	private boolean isExempt(String condition) {
		DictInfo agentInfo = wmkgDictService.getSysDictInfo("年龄");
		if (Objects.isNull(agentInfo)) {
			return false;
		}
		List<DictItemInfo> dictItemList = agentInfo.getDictItemList();
		for (DictItemInfo dictItemInfo : dictItemList) {
			if (dictItemInfo.getLabel().equals(condition)) {
				return true;
			}
		}
		return false;
	}

}
