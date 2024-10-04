package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.DataDto;
import com.aeye.aeaimb.hugegraph.controller.dto.ReasonFactorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SaveDataErrorDto;
import com.aeye.aeaimb.hugegraph.mapper.WmkgReasonFactorMapper;
import com.aeye.aeaimb.hugegraph.mapper.entity.*;
import com.aeye.aeaimb.hugegraph.service.*;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.hugegraph.structure.graph.Edge;
import org.apache.hugegraph.structure.graph.Vertex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 推理因子分类(WmkgReasonFactor)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-03 09:37:00
 */
@Service("wmkgReasonFactorService")
@Slf4j
public class WmkgReasonFactorServiceImpl extends ServiceImpl<WmkgReasonFactorMapper, WmkgReasonFactor> implements WmkgReasonFactorService {
	@Resource
	private WmkgReasonDiseaseService wmkgReasonDiseaseService;
	@Resource
	private WmkgHugeGraphService wmkgHugeGraphService;
	@Resource
	private WmkgReasonSymptomService wmkgReasonSymptomService;
	@Resource
	private WmkgReasonExamService wmkgReasonExamService;
	@Resource
	private WmkgReasonLabtestService wmkgReasonLabtestService;

	@Resource
	private WmkgReasonExamItemService wmkgReasonExamItemService;
	@Resource
	private WmkgReasonLabtestItemService wmkgReasonLabtestItemService;
	@Resource
	private WmkgReasonOpService wmkgReasonOpService;

	@Override
	public List<SaveDataErrorDto> batchReasonFactor(List<ReasonFactorDto> dtoList) {
		log.info("数据初始化开始");
		List<WmkgReasonFactor> reasonFactors = new ArrayList<>();
		for (ReasonFactorDto dto : dtoList) {
			WmkgReasonFactor wmkgReasonFactor = new WmkgReasonFactor();
			BeanUtil.copyProperties(dto, wmkgReasonFactor);
			reasonFactors.add(wmkgReasonFactor);
		}
		this.saveBatch(reasonFactors);
		log.info("数据初始化结束");
		return null;
	}

	@Override
	public List<SaveDataErrorDto> batchDiagReasonObjects(List<DataDto> dtoList) {
		List<SaveDataErrorDto> errorList = new ArrayList<>();
		log.info("数据初始化开始");

		//遍历设置疾病的相关顶点数据
		Map<String, List<DataDto>> rootMap = dtoList.stream().collect(Collectors.groupingBy(DataDto::getProperty));

		//保存推理疾病
		List<DataDto> diagCode = rootMap.get("疾病编码");
		List<DataDto> diagName = rootMap.get("疾病名称");
		WmkgReasonDisease reasonDisease = wmkgReasonDiseaseService.getOne(Wrappers.<WmkgReasonDisease>lambdaQuery()
				.eq(WmkgReasonDisease::getDiseaseCode, diagCode.get(0).getPropertyValue())
				.eq(WmkgReasonDisease::getDiseaseName, diagName.get(0).getPropertyValue()));
		if (Objects.isNull(reasonDisease)){
			wmkgReasonDiseaseService.save(new WmkgReasonDisease().setDiseaseCode(diagCode.get(0).getPropertyValue())
					.setDiseaseName(diagName.get(0).getPropertyValue()));
		}

		//保存推理症状
		List<DataDto> smpList = wmkgHugeGraphService.findListByFuzzyKey(rootMap, "症状");
		for (DataDto smp : smpList) {
			String value = smp.getPropertyValue();
			String[] split = value.split("#");
			WmkgReasonSymptom one = wmkgReasonSymptomService.getOne(Wrappers.<WmkgReasonSymptom>lambdaQuery()
					.eq(WmkgReasonSymptom::getSymptomName, split[0]));
			if (Objects.isNull(one)) {
				wmkgReasonSymptomService.save(new WmkgReasonSymptom().setSymptomName(split[0]));
			}
		}

		//保存推理检查
		List<DataDto> check = wmkgHugeGraphService.findListByFuzzyKey(rootMap, "检查项目");
		Map<String, List<DataDto>> checkMap = check.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		Set<Map.Entry<String, List<DataDto>>> checkEntries = checkMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : checkEntries) {
			String key = entry.getKey();
			if (StrUtil.isBlank(key) || "无".equals(key)) {
				continue;
			}
			String[] split = key.split("#");
			String examName = split[0];
			WmkgReasonExam reasonExam = wmkgReasonExamService.getOne(Wrappers.<WmkgReasonExam>lambdaQuery().eq(WmkgReasonExam::getExamName, examName));
			if (Objects.isNull(reasonExam)){
				//保存检验项目
				reasonExam = new WmkgReasonExam().setExamName(examName);
				wmkgReasonExamService.save(reasonExam);
			}

			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			Set<Map.Entry<String, List<DataDto>>> entries1 = collect.entrySet();
			for (Map.Entry<String, List<DataDto>> entry1 : entries1) {
				String key1 = entry1.getKey();
				if ("异常指标".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> zbMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = zbMap.entrySet();
					for (Map.Entry<String, List<DataDto>> entry2 : entries2) {
						String zbName = entry2.getKey();

						WmkgReasonExamItem one = wmkgReasonExamItemService.getOne(Wrappers.<WmkgReasonExamItem>lambdaQuery()
								.eq(WmkgReasonExamItem::getExamCode, reasonExam.getExamCode())
								.eq(WmkgReasonExamItem::getExamItem, zbName));
						if (Objects.isNull(one)) {
							WmkgReasonExamItem wmkgReasonExamItem = new WmkgReasonExamItem();
							wmkgReasonExamItem.setExamCode(reasonExam.getExamCode());
							wmkgReasonExamItem.setExamName(reasonExam.getExamName());
							wmkgReasonExamItem.setExamItem(zbName);
							List<DataDto> value2 = entry2.getValue();
							for (DataDto dataDto : value2) {
								String zhibiaoFlag = dataDto.getPropertyValue3();
								String zhibiaoValue = dataDto.getPropertyValue4();
								if (StrUtil.isBlank(zhibiaoValue) || "无".equals(zhibiaoValue)) {
									continue;
								}
								if ("单位".equals(zhibiaoFlag)) {
									wmkgReasonExamItem.setUnit(zhibiaoValue);
								}
							}
							wmkgReasonExamItemService.save(wmkgReasonExamItem);
						}

					}
				}
			}
		}

		//保存推理检验
		List<DataDto> verify = wmkgHugeGraphService.findListByFuzzyKey(rootMap, "检验项目");
		Map<String, List<DataDto>> verifuyMap = verify.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		Set<Map.Entry<String, List<DataDto>>> entries = verifuyMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : entries) {
			String key = entry.getKey();
			if (StrUtil.isBlank(key) || "无".equals(key)) {
				continue;
			}
			String[] split = key.split("#");
			String labtestName = split[0];
			WmkgReasonLabtest reasonLabtest = wmkgReasonLabtestService.getOne(Wrappers.<WmkgReasonLabtest>lambdaQuery().eq(WmkgReasonLabtest::getLabtestName, labtestName));
			if (Objects.isNull(reasonLabtest)){
				//保存检验项目
				reasonLabtest = new WmkgReasonLabtest().setLabtestName(labtestName);
				wmkgReasonLabtestService.save(reasonLabtest);
			}

			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			Set<Map.Entry<String, List<DataDto>>> entries1 = collect.entrySet();
			for (Map.Entry<String, List<DataDto>> entry1 : entries1) {
				String key1 = entry1.getKey();
				if ("异常指标".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> zbMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = zbMap.entrySet();
					for (Map.Entry<String, List<DataDto>> entry2 : entries2) {
						String zbName = entry2.getKey();

						String[] zbNameArr = zbName.split("、");
						for (String zbNameItem : zbNameArr){
							WmkgReasonLabtestItem wmkgReasonLabtestItem = wmkgReasonLabtestItemService.getOne(Wrappers.<WmkgReasonLabtestItem>lambdaQuery()
									.eq(WmkgReasonLabtestItem::getLabtestCode, reasonLabtest.getLabtestCode())
									.eq(WmkgReasonLabtestItem::getLabtestItem, zbNameItem));
							if (Objects.isNull(wmkgReasonLabtestItem)){
								wmkgReasonLabtestItem = new WmkgReasonLabtestItem();
								wmkgReasonLabtestItem.setLabtestCode(reasonLabtest.getLabtestCode());
								wmkgReasonLabtestItem.setLabtestName(reasonLabtest.getLabtestName());
								wmkgReasonLabtestItem.setLabtestItem(zbNameItem);
								List<DataDto> value2 = entry2.getValue();
								for (DataDto dataDto : value2) {
									String zhibiaoFlag = dataDto.getPropertyValue3();
									String zhibiaoValue = dataDto.getPropertyValue4();
									if (StrUtil.isBlank(zhibiaoValue) || "无".equals(zhibiaoValue)) {
										continue;
									}
									if ("单位".equals(zhibiaoFlag)) {
										wmkgReasonLabtestItem.setUnit(zhibiaoValue);
									}
								}
								wmkgReasonLabtestItemService.save(wmkgReasonLabtestItem);
							}
						}
					}
				}
			}
		}

		//保存推理手术
		List<DataDto> operates = wmkgHugeGraphService.findListByFuzzyKey(rootMap, "手术");
		for (DataDto val : operates) {
			String value = val.getPropertyValue();
			if ("无".equals(value)) {
				continue;
			}
			String[] split = value.split("；");
			String opName = split[0];
			WmkgReasonOp one = wmkgReasonOpService.getOne(Wrappers.<WmkgReasonOp>lambdaQuery().eq(WmkgReasonOp::getOpName, opName));
			if (Objects.nonNull(one)){
				continue;
			}
			WmkgReasonOp wmkgReasonOp = new WmkgReasonOp().setOpName(opName).setOpCode(split[1]);
			wmkgReasonOpService.save(wmkgReasonOp);
		}

		log.info("数据初始化结束");
		return errorList;
	}
}
