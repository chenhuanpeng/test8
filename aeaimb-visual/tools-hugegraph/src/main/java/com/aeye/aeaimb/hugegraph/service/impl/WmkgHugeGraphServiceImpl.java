package com.aeye.aeaimb.hugegraph.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.config.HGFactory;
import com.aeye.aeaimb.hugegraph.constant.CommonConstants;
import com.aeye.aeaimb.hugegraph.controller.dto.*;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseDict;
import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseSpecial;
import com.aeye.aeaimb.hugegraph.service.WmkgDictService;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseDictService;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseSpecialService;
import com.aeye.aeaimb.hugegraph.service.WmkgHugeGraphService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.hugegraph.driver.GraphManager;
import org.apache.hugegraph.driver.HugeClient;
import org.apache.hugegraph.driver.SchemaManager;
import org.apache.hugegraph.structure.graph.Edge;
import org.apache.hugegraph.structure.graph.Vertex;
import org.apache.hugegraph.structure.schema.VertexLabel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * [西医]科室(WmkgDept)表服务实现类
 *
 * @author linkeke
 * @since 2024-06-24 11:19:05
 */
@Service("wmkgDeptService")
@Slf4j
public class WmkgHugeGraphServiceImpl implements WmkgHugeGraphService {

	@Resource
	WmkgDictService wmkgDictService;
	@Resource
	WmkgDiseaseDictService wmkgDiseaseDictService;

	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDiseaseSpecialService wmkgDiseaseSpecialService;

	@Override
	public void initSchemeVertex(List<SchemaDto> schemaDtoList) {
		log.info("属性和顶点初始化开始");
		HugeClient hugeClient = HGFactory.ins();
		SchemaManager schema = hugeClient.schema();

		int i = 1;
		for (SchemaDto schemaDto : schemaDtoList) {
			log.info("属性和顶点行数为:" + i + "条，总行数为:" + schemaDtoList.size());
			i++;

			//创建属性
			String[] properties = schemaDto.getProperty().split("，");
			//设置主键
			String primaryKey = properties[0];
			List<String> nullKeys = new ArrayList<>(properties.length - 1);
			for (String prop : properties) {
				schema.propertyKey(prop).asText().ifNotExist().create();
				if (!primaryKey.equals(prop)) {
					nullKeys.add(prop);
				}
			}

			//创建顶点
			String vertex = schemaDto.getVertex();
			schema.vertexLabel(vertex)
					.properties(properties)
					.nullableKeys(nullKeys.toArray(new String[0]))
					.primaryKeys(primaryKey)
					.ifNotExist()
					.create();
		}
		hugeClient.close();
		log.info("属性和顶点初始化结束");

	}

	@Override
	public List<SchemaEdgeErrorDto> initSchemeEdge(List<SchemaEdgeDto> schemaDtoList) {
		log.info("边关系初始化开始");
		HugeClient hugeClient = HGFactory.ins();
		SchemaManager schema = hugeClient.schema();
		int i = 1;

		List<SchemaEdgeErrorDto> errorList = new ArrayList<>();

		for (SchemaEdgeDto schemaEdgeDto : schemaDtoList) {
			log.info("边关系行数为:" + i + "条，总行数为:" + schemaDtoList.size());
			i++;

			//判断顶点是否存在
			VertexLabel vertexLabel = schema.getVertexLabel(schemaEdgeDto.getSourceVertex());
			if (!vertexLabel.checkExist()) {
				SchemaEdgeErrorDto errorDto = new SchemaEdgeErrorDto();
				BeanUtil.copyProperties(schemaEdgeDto, errorDto);
				errorDto.setErrorMsg("源顶点不存在：" + schemaEdgeDto.getSourceVertex());
				errorList.add(errorDto);
				continue;
			}
			VertexLabel vertexLabel1 = schema.getVertexLabel(schemaEdgeDto.getTargetVertex());
			if (!vertexLabel1.checkExist()) {
				SchemaEdgeErrorDto errorDto = new SchemaEdgeErrorDto();
				BeanUtil.copyProperties(schemaEdgeDto, errorDto);
				errorDto.setErrorMsg("目标顶点不存在：" + schemaEdgeDto.getTargetVertex());
				errorList.add(errorDto);
				continue;
			}

			//边属性
			String[] linkProperties = {};
			if (StrUtil.isNotBlank(schemaEdgeDto.getLinkProperty())) {
				linkProperties = schemaEdgeDto.getLinkProperty().split("，");
				for (String prop : linkProperties) {
					schema.propertyKey(prop).asText().ifNotExist().create();
				}
			}

			schema.edgeLabel(schemaEdgeDto.getLink())
					.sourceLabel(schemaEdgeDto.getSourceVertex())
					.targetLabel(schemaEdgeDto.getTargetVertex())
					.properties(linkProperties)
					.nullableKeys(linkProperties)
					.ifNotExist()
					.create();
		}
		log.info("边关系初始化结束");
		hugeClient.close();
		return errorList;
	}

	@Override
	public List<FileErrorDto> unmergeCell(File file) throws IOException {

		List<FileErrorDto> errorList = new ArrayList<>();

		String destPath = "E:\\西医图谱\\导入标准模板\\特殊疾病\\取消合并单元格";
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				errorList.addAll(wrap(f, destPath));
			} else {
				InputStream in = new FileInputStream(f);
				Workbook wb = WorkbookFactory.create(in);
				// 如果指定sheet名,则取指定sheet中的内容 否则默认指向第1个sheet
				Sheet sheet = wb.getSheetAt(0);
				if (sheet == null) {
					throw new IOException("文件sheet不存在");
				}
				//合并单元格数量
				for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
					//合并单元格位置(地址)
					CellRangeAddress range = sheet.getMergedRegion(i);
					//拆分单元格
					sheet.removeMergedRegion(i);
					int firstColumn = range.getFirstColumn();
					int firstRow = range.getFirstRow();
					int lastRow = range.getLastRow();
					Cell cfirst = sheet.getRow(firstRow).getCell(firstColumn);
					try {
						//填充被拆分单元格的值
						for (int m = firstRow + 1; m <= lastRow; m++) {
							Cell tc = sheet.getRow(m).getCell(firstColumn);
							tc.setCellStyle(cfirst.getCellStyle());
							tc.setCellValue(cfirst.getStringCellValue());
						}
					}catch (Exception e){
						log.info("firstColumn:{},firstRow:{},lastRow:{}",firstColumn,firstRow,lastRow);
					}

				}

				// 创建新的第一行（实际上会插入到现有第一行之前）
				sheet.shiftRows(0, sheet.getLastRowNum(), 1);

				// 创建第一行（Row对象，索引从0开始）
				Row row = sheet.createRow(0); // 索引0即为第一行
				// 在第一行中创建单元格并设置值（Cell对象，索引也是从0开始）
				Cell cell0 = row.createCell(0);
				cell0.setCellValue("疾病属性");
				Cell cell1 = row.createCell(1);
				cell1.setCellValue("属性值");
				Cell cell2 = row.createCell(2);
				cell2.setCellValue("属性值1");
				Cell cell3 = row.createCell(3);
				cell3.setCellValue("属性值2");
				Cell cell4 = row.createCell(4);
				cell4.setCellValue("属性值3");
				Cell cell5 = row.createCell(5);
				cell5.setCellValue("属性值4");
				Cell cell6 = row.createCell(6);
				cell6.setCellValue("属性值5");
				FileOutputStream fos = new FileOutputStream(destPath + File.separator + f.getName());
				log.info(f.getName() + ":文件写入成功");
				wb.write(fos);
				fos.close();
			}
		}

		return errorList;
	}


	public List<FileErrorDto> wrap(File file, String destPath) {
		List<FileErrorDto> errorList = new ArrayList<>();
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				wrap(f, destPath);
			} else {
				try {
					InputStream in = new FileInputStream(f);
					Workbook wb = WorkbookFactory.create(in);
					// 如果指定sheet名,则取指定sheet中的内容 否则默认指向第1个sheet
					Sheet sheet = wb.getSheetAt(0);
					if (sheet == null) {
						throw new IOException("文件sheet不存在");
					}
					//合并单元格数量
					for (int i = sheet.getNumMergedRegions() - 1; i >= 0; i--) {
						//合并单元格位置(地址)
						CellRangeAddress range = sheet.getMergedRegion(i);
						//拆分单元格
						sheet.removeMergedRegion(i);
						int firstColumn = range.getFirstColumn();
						int firstRow = range.getFirstRow();
						int lastRow = range.getLastRow();
						Cell cfirst = sheet.getRow(firstRow).getCell(firstColumn);


						try {
							//填充被拆分单元格的值
							for (int m = firstRow + 1; m <= lastRow; m++) {
								Cell tc = sheet.getRow(m).getCell(firstColumn);
								tc.setCellStyle(cfirst.getCellStyle());
								tc.setCellValue(cfirst.getStringCellValue());
							}
						}catch (Exception e){
							log.info("firstColumn:{},firstRow:{},lastRow:{}",firstColumn,firstRow,lastRow);
						}

						//填充被拆分单元格的值
//						for (int m = firstRow + 1; m <= lastRow; m++) {
//							Cell tc = sheet.getRow(m).getCell(firstColumn);
//							tc.setCellStyle(cfirst.getCellStyle());
//							tc.setCellValue(cfirst.getStringCellValue());
//						}
					}

					// 创建新的第一行（实际上会插入到现有第一行之前）
					sheet.shiftRows(0, sheet.getLastRowNum(), 1);

					// 创建第一行（Row对象，索引从0开始）
					Row row = sheet.createRow(0); // 索引0即为第一行
					// 在第一行中创建单元格并设置值（Cell对象，索引也是从0开始）
					Cell cell0 = row.createCell(0);
					cell0.setCellValue("疾病属性");
					Cell cell1 = row.createCell(1);
					cell1.setCellValue("属性值");
					Cell cell2 = row.createCell(2);
					cell2.setCellValue("属性值1");
					Cell cell3 = row.createCell(3);
					cell3.setCellValue("属性值2");
					Cell cell4 = row.createCell(4);
					cell4.setCellValue("属性值3");
					Cell cell5 = row.createCell(5);
					cell5.setCellValue("属性值4");
					Cell cell6 = row.createCell(6);
					cell6.setCellValue("属性值5");
					FileOutputStream fos = new FileOutputStream(destPath + File.separator + f.getName());
					log.info(f.getName() + ":文件写入成功");
					wb.write(fos);
					fos.close();
				} catch (Exception e) {
					log.error(f.getName() + ":文件写入失败", e);
					FileErrorDto errorDto = new FileErrorDto();
					errorDto.setFileName(f.getName());
					errorDto.setErrorMsg(e.getMessage());
					errorList.add(errorDto);
				}
			}
		}

		return errorList;

	}

	@Override
	public List<SaveDataErrorDto> batchSaveData(List<DataDto> schemaDtoList) {

		List<SaveDataErrorDto> errorList = new ArrayList<>();

		log.info("数据初始化开始");
		HugeClient hugeClient = HGFactory.ins();
		GraphManager graph = hugeClient.graph();

		//遍历设置疾病的相关顶点数据
		Vertex vertex_diag = getVertexDiag(schemaDtoList, graph, errorList);

		Map<String, List<DataDto>> rootMap = schemaDtoList.stream().collect(Collectors.groupingBy(DataDto::getProperty));

		//体格检查
//		List<DataDto> physicalExamList = rootMap.get("体格检查");
		List<DataDto> physicalExamList = findListByFuzzyKey(rootMap, "体格检查");
		savePhysicalExamData(physicalExamList, graph, vertex_diag, errorList);

		//症状顶点
//		List<DataDto> smpList = rootMap.get("症状");
		List<DataDto> smpList = findListByFuzzyKey(rootMap, "症状");
		saveSymptomData(smpList, graph, vertex_diag, errorList);

		//多发要素顶点
//		List<DataDto> multiple = rootMap.get("多发因素");
		List<DataDto> multiple = findListByFuzzyKey(rootMap, "多发因素");
		saveMultipleData(multiple, graph, vertex_diag, errorList);

		//检验顶点
//		List<DataDto> verify = rootMap.get("检验项目");
		List<DataDto> verify = findListByFuzzyKey(rootMap, "检验项目");
		saveVerifyData(verify, graph, vertex_diag, errorList);

		//检查顶点
//		List<DataDto> check = rootMap.get("检查项目");
		List<DataDto> check = findListByFuzzyKey(rootMap, "检查项目");
		saveCheckData(check, graph, vertex_diag, errorList);

		//鉴别诊断
		List<DataDto> differentialDiag = rootMap.get("鉴别诊断");
		saveDifferentialDaigData(differentialDiag, graph, vertex_diag,errorList);

		//药品顶点
		List<DataDto> drug = rootMap.get("药品");
		saveDrugData(drug, graph, vertex_diag,errorList);

		//操作顶点
		List<DataDto> operate = rootMap.get("操作");
		saveOperateData(operate, graph, vertex_diag, errorList);

		//手术顶点
		List<DataDto> surgery = rootMap.get("手术");
		saveSurgeryData(surgery, graph, vertex_diag, errorList);

		hugeClient.close();
		return errorList;
	}

	@Override
	public List<SaveDataErrorDto> batchSaveSpecial(List<DataDto> schemaDtoList) {
		Map<String, List<DataDto>> rootMap = schemaDtoList.stream().collect(Collectors.groupingBy(DataDto::getProperty));
		List<DataDto> special = rootMap.get("特殊疾病处置建议");
		List<DataDto> code = rootMap.get("疾病编码");
		String diseaseCOde = code.get(0).getPropertyValue();

		for (DataDto dataDto : special){
			String label = dataDto.getPropertyValue();
			String value = dataDto.getPropertyValue1();
			if (StrUtil.isNotBlank(value)){
				WmkgDiseaseSpecial wmkgDiseaseSpecial = new WmkgDiseaseSpecial();
				wmkgDiseaseSpecial.setDiseaseLabel(label);
				wmkgDiseaseSpecial.setDiseaseCode(diseaseCOde);
				wmkgDiseaseSpecial.setDiseaseDisposal(value);
				wmkgDiseaseSpecialService.save(wmkgDiseaseSpecial);
			}
		}

		return null;
	}

	public List<DataDto> findListByFuzzyKey(Map<String, List<DataDto>> map, String fuzzyKey) {
		List<DataDto> result = new ArrayList<>();
		for (Map.Entry<String, List<DataDto>> entry : map.entrySet()) {
			if (entry.getKey().toLowerCase().contains(fuzzyKey.toLowerCase())) {
				result.addAll(entry.getValue());
			}
		}
		return result;
	}

	/**
	 * 保存药品顶点
	 *
	 * @param drug
	 * @param graph
	 * @param vertex_diag
	 */
	private void saveDrugData(List<DataDto> drug, GraphManager graph, Vertex vertex_diag,List<SaveDataErrorDto> errorList) {
		List<DataDto> drug1 = drug.stream().filter(dto -> !CommonConstants.groupName.contains(dto.getPropertyValue())).collect(Collectors.toList());
		List<DataDto> drug2 = drug.stream().filter(dto -> CommonConstants.groupName.contains(dto.getPropertyValue())).collect(Collectors.toList());

		Map<String, List<DataDto>> drugCateMap = drug1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
		for (Map.Entry<String, List<DataDto>> entry : drugCateMap.entrySet()) {
			String drugCateName = entry.getKey();
			if (StrUtil.isBlank(drugCateName) || drugCateName.contains("无")) {
				continue;
			}
			Map<String, Object> drugCateMapJb = new HashMap<>();
			drugCateMapJb.put("名称", drugCateName);
			drugCateMapJb.put("编码", drugCateName);
			Vertex vertex_drugCate = getVertex(graph, "药品分类", drugCateMapJb);
			log.info("药品分类顶点：" + vertex_drugCate.id());

			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect1 = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
			for (Map.Entry<String, List<DataDto>> entry1 : collect1.entrySet()) {
				String drugName = entry1.getKey();
				if (StrUtil.isBlank(drugName)) {
					continue;
				}
				String[] drugNameArr = drugName.split("；");
				Map<String, Object> drugMapJb = new HashMap<>();
				drugMapJb.put("编码", drugNameArr[1]);
				drugMapJb.put("名称", drugNameArr[0]);
				Vertex vertex_drug = getVertex(graph, "药品", drugMapJb);
				log.info("药品顶点：" + vertex_drug.id());

				//添加所属药品分类
				vertex_drug.addEdge("所属药品分类", vertex_drugCate);
				String useFlag = "否";
				List<DataDto> value1 = entry1.getValue();
				Map<String, List<DataDto>> collect = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue3));
				for (Map.Entry<String, List<DataDto>> entry2 : collect.entrySet()) {
					String key1 = entry2.getKey();
					if ("是否主要用药".equals(key1)) {
						List<DataDto> value2 = entry2.getValue();
						DataDto dto = value2.get(0);
						useFlag = dto.getPropertyValue4();
					}
				}
				//添加疾病_治疗_药品关系
				Edge property = new Edge("疾病_治疗_药品").source(vertex_diag).target(vertex_drug).property("默认用药", useFlag).property("治疗药品分类", drugCateName);
				graph.addEdge(property);
				log.info("疾病_治疗_药品关系：" + vertex_diag.property("名称") + "->" + vertex_drug.id());

				collect.entrySet().forEach(entry2 -> {
					String key1 = entry2.getKey();
					if ("用法用量".equals(key1)) {
						List<DataDto> value2 = entry2.getValue();
						Map<String, Object> drugUseMapJb = new HashMap<>();
						for (DataDto dataDto : value2) {
							if (StrUtil.isBlank(dataDto.getPropertyValue5()) || "无".equals(dataDto.getPropertyValue5())) {
								continue;
							}
							if ("剂型".equals(dataDto.getPropertyValue4())){
								Boolean existInDict = wmkgDictService.existInDict("剂型", dataDto.getPropertyValue5());
								if (!existInDict) {
									SaveDataErrorDto errorDto = new SaveDataErrorDto();
									errorDto.setErrorMsg("剂型值不在枚举值范围内：" + dataDto.getPropertyValue5());
									errorDto.setProperty(getErrorProperties(dataDto));
									errorList.add(errorDto);
									continue;
								}
							}
							drugUseMapJb.put(dataDto.getPropertyValue4(), dataDto.getPropertyValue5());
						}
						//添加用法用量顶点
						Vertex vertex_drug_use = graph.addVertex("药品用量", drugUseMapJb);
						log.info("药品用量顶点：" + vertex_drug_use.id());
						//添加药品_用量关系
						graph.addEdge(new Edge("药品_用量").source(vertex_drug).target(vertex_drug_use).property("关联疾病", vertex_diag.property("名称")));
						log.info("药品_用量关系：" + vertex_drug.id() + "->" + vertex_drug_use.id());
					}
					if ("诊断性治疗".equals(key1)) {
						List<DataDto> value2 = entry2.getValue();
						if (CollUtil.isNotEmpty(value2)) {
							DataDto dto = value2.get(0);
							Map<String, Object> drugIsUseMapJb = new HashMap<>();
							drugIsUseMapJb.put("编码", key1);
							drugIsUseMapJb.put("药效", dto.getPropertyValue4());
							//添加药效顶点
							Vertex vertex_drug_use = getVertex(graph, "药品效果", drugIsUseMapJb);
							log.info("药品效果顶点：" + vertex_drug_use.id());
							//添加药品_药效关系
							Edge edge = new Edge("药品_效果").source(vertex_drug).target(vertex_drug_use).property("关联疾病", vertex_diag.property("名称"));
							String weight = dto.getPropertyValue5();
							if (StrUtil.isNotBlank(weight)) {
								edge.property("权重", weight);
							}
							graph.addEdge(edge);
							log.info("药品_效关系：" + vertex_drug.id() + "->" + vertex_drug_use.id());
						}
					}
				});
			}
		}

		Map<String, List<DataDto>> drugMap = drug2.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		for (Map.Entry<String, List<DataDto>> entry : drugMap.entrySet()) {
			String drugCateName = entry.getKey();
			List<DataDto> value = entry.getValue();
			Map<String, Object> drugGroupMapJb = new HashMap<>();
			drugGroupMapJb.put("组号", drugCateName);
			drugGroupMapJb.put("编码", drugCateName);
			Vertex vertex_drugGroup = getVertex(graph, "用药组号", drugGroupMapJb);
			log.info("用药组号顶点：" + vertex_drugGroup.id());

			//疾病_用药分组_组号边关系
			vertex_diag.addEdge("疾病_用药分组_组号", vertex_drugGroup);

			for (DataDto dataDto : value) {
				Map<String, Object> drugCateMapJb = new HashMap<>();
				drugCateMapJb.put("名称", dataDto.getPropertyValue1());
				drugCateMapJb.put("编码", dataDto.getPropertyValue1());
				Vertex vertex_drugCate = getVertex(graph, "药品分类", drugCateMapJb);
				log.info("药品分类顶点：" + vertex_drugCate.id());

				String drugName = dataDto.getPropertyValue2();
				if (StrUtil.isBlank(drugName)) {
					continue;
				}
				String[] drugNameArr = drugName.split("；");
				Map<String, Object> drugMapJb = new HashMap<>();
				drugMapJb.put("编码", drugNameArr[1]);
				drugMapJb.put("名称", drugNameArr[0]);
				Vertex vertex_drug = getVertex(graph, "药品", drugMapJb);
				log.info("药品顶点：" + vertex_drug.id());

				//添加所属药品分类
				graph.addEdge(new Edge("所属药品分类").source(vertex_drug).target(vertex_drugCate));
				//用药组号包含药品
				graph.addEdge(new Edge("用药组号_包含_药品").source(vertex_drugGroup).target(vertex_drug)
						.property("关联疾病", vertex_diag.property("名称"))
						.property("治疗药品分类", dataDto.getPropertyValue1()));
			}
		}
	}

	/**
	 * 保存操作数据
	 *
	 * @param operate
	 * @param graph
	 * @param vertex_diag
	 */
	private void saveOperateData(List<DataDto> operate, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> operateMap = operate.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		for (Map.Entry<String, List<DataDto>> entry : operateMap.entrySet()) {
			String operateName = entry.getKey();
			if ("无".equals(operateName)) {
				continue;
			}
			String[] operateNameArr = operateName.split("；");
			Map<String, Object> operateMapJb = new HashMap<>();
			operateMapJb.put("编码", operateNameArr[1]);
			operateMapJb.put("名称", operateNameArr[0]);
			List<DataDto> value3 = entry.getValue();
			String collect1 = value3.stream()
					.filter(item -> "说明".equals(item.getPropertyValue1()))
					.map(DataDto::getPropertyValue2)
					.filter(Objects::nonNull)
					.filter(item -> !"无".equals(item))
					.collect(Collectors.joining(","));
			if (StrUtil.isNotBlank(collect1)) {
				operateMapJb.put("注意事项", collect1);
			}
			Vertex vertex_operate = getVertex(graph, "操作", operateMapJb);
			log.info("操作顶点：" + vertex_operate.id());

			//添加疾病_治疗_操作关系
			vertex_diag.addEdge("疾病_治疗_操作", vertex_operate);
			log.info("疾病_治疗_操作关系：" + vertex_diag.property("名称") + "->" + vertex_operate.id());

			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			collect.entrySet().forEach(entry1 -> {
				String key1 = entry1.getKey();
				if ("合理性审查".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> hlMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = hlMap.entrySet();
					//保存合理性因子值
					saveHlValue(graph, vertex_diag, errorList, entries2, vertex_operate);
				}
			});
		}
	}

	/**
	 * 保存手术
	 *
	 * @param surgery
	 * @param graph
	 * @param vertex_diag
	 */
	private void saveSurgeryData(List<DataDto> surgery, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> surgeryMap = surgery.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		for (Map.Entry<String, List<DataDto>> entry : surgeryMap.entrySet()) {
			String surgeryName = entry.getKey();
			if ("无".equals(surgeryName)) {
				continue;
			}
			String[] surgeryNameArr = surgeryName.split("；");
			Map<String, Object> surgeryMapJb = new HashMap<>();
			surgeryMapJb.put("编码", surgeryNameArr[1]);
			surgeryMapJb.put("名称", surgeryNameArr[0]);
			Vertex vertex_surgery = getVertex(graph, "手术", surgeryMapJb);
			log.info("手术顶点：" + vertex_surgery.id());

			List<DataDto> value3 = entry.getValue();
			String collect1 = value3.stream()
					.filter(item -> "说明".equals(item.getPropertyValue1()))
					.map(DataDto::getPropertyValue2)
					.filter(Objects::nonNull)
					.filter(item -> !"无".equals(item))
					.collect(Collectors.joining(","));
			//添加疾病_治疗_手术关系
			graph.addEdge(new Edge("疾病_治疗_手术").source(vertex_diag).target(vertex_surgery).property("注意事项", collect1));
			log.info("疾病_治疗_手术关系：" + vertex_diag.property("名称") + "->" + vertex_surgery.id());

			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			collect.entrySet().forEach(entry1 -> {
				String key1 = entry1.getKey();
				if ("合理性审查".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> hlMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = hlMap.entrySet();
					//保存合理性因子值
					saveHlValue(graph, vertex_diag, errorList, entries2, vertex_surgery);
				}
			});
		}
	}

	private void saveDifferentialDaigData(List<DataDto> differentialDiag, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> differentialDiagMap = differentialDiag.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		Set<Map.Entry<String, List<DataDto>>> entries1 = differentialDiagMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : entries1) {
			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			Set<Map.Entry<String, List<DataDto>>> entries2 = collect.entrySet();
			for (Map.Entry<String, List<DataDto>> entrya : entries2) {
				List<DataDto> value2 = entrya.getValue();
				if (CollUtil.isNotEmpty(value2)) {
					DataDto dto = value2.get(0);
					String diagName = dto.getPropertyValue();
					Map<String, Object> diagObjectMapJb = new HashMap<>();
					WmkgDiseaseDict diseaseDict = wmkgDiseaseDictService.getDiseaseDict(diagName);
					if (Objects.nonNull(diseaseDict)) {
						diagObjectMapJb.put("编码", diseaseDict.getDiseaseCode());
					}else {
						diagObjectMapJb.put("编码", diagName);
					}
					diagObjectMapJb.put("名称", diagName);
					//添加疾病顶点
					Vertex vertex_diag_1 = getVertex(graph, "疾病", diagObjectMapJb);
					log.info("疾病顶点：" + vertex_diag_1.id());
					//添加疾病_鉴别诊断_疾病关系
					String desc = dto.getPropertyValue2();
					vertex_diag.addEdge("疾病_鉴别诊断_疾病", vertex_diag_1)
							.property("鉴别说明", desc);
					log.info("疾病_鉴别诊断_疾病关系：" + vertex_diag.property("名称") + "->" + vertex_diag_1.id());

				}
			}
		}
	}

	private void saveCheckData(List<DataDto> check, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> checkMap = check.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		for (Map.Entry<String, List<DataDto>> entry : checkMap.entrySet()) {
			String key = entry.getKey();
			if (StrUtil.isBlank(key) || "无".equals(key)) {
				continue;
			}
			String[] split = key.split("#");
			key = split[0];
			Map<String, Object> checkMapObjectMap = new HashMap<>();
			checkMapObjectMap.put("编码", key);
			checkMapObjectMap.put("名称", key);
			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			collect.forEach((key1, value1) -> {
                if ("注意事项".equals(key1)) {
                    String collect1 = value1.stream().map(DataDto::getPropertyValue2).filter(Objects::nonNull)
                            .filter(item -> !"无".equals(item)).collect(Collectors.joining(","));
                    if (StrUtil.isNotBlank(collect1)) {
                        checkMapObjectMap.put("注意事项", collect1);
                    }
                }
            });

			//添加检查顶点
			Vertex vertex_check = getVertex(graph, "检查", checkMapObjectMap);
			log.info("检查顶点：" + vertex_check.id());
			//添加疾病_治疗_检查关系
			if (split.length > 1) {
				vertex_diag.addEdge("疾病_治疗_检查", vertex_check).property("聚类分组", split[1]);
			} else {
				vertex_diag.addEdge("疾病_治疗_检查", vertex_check);
			}
			log.info("疾病_治疗_检查关系：" + vertex_diag.property("名称") + "->" + vertex_check.id());

			Set<Map.Entry<String, List<DataDto>>> entries1 = collect.entrySet();
			for (Map.Entry<String, List<DataDto>> entry1 : entries1) {
				String key1 = entry1.getKey();
				if ("异常指标".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> zbMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = zbMap.entrySet();
					for (Map.Entry<String, List<DataDto>> entry2 : entries2) {
						String zbName = entry2.getKey();
						//指标顶点
						Map<String, Object> zhibiaoObjectMap = new HashMap<>();
						zhibiaoObjectMap.put("编码", zbName);
						zhibiaoObjectMap.put("名称", zbName);
						List<DataDto> value2 = entry2.getValue();
						//指标值顶点
						String vertex_zbValue_weight = "";
						Map<String, Object> dlZbValueObjectMap = new HashMap<>();
						List<Map<String, Object>> dxZbValueObjectMap = new ArrayList<>();
						for (DataDto dataDto : value2) {
							vertex_zbValue_weight = dataDto.getPropertyValue5();
							String zhibiaoFlag = dataDto.getPropertyValue3();
							String zhibiaoValue = dataDto.getPropertyValue4();
							if (StrUtil.isBlank(zhibiaoValue) || "无".equals(zhibiaoValue)) {
								continue;
							}
							if ("运算符".equals(zhibiaoFlag)) {
								//校验定量值
								try {
									JexlBuilder jexlBuilder = new JexlBuilder();
									JexlEngine jexlEngine = jexlBuilder.create();
									jexlEngine.createExpression(zhibiaoValue);
								} catch (Exception e) {
									SaveDataErrorDto errorDto = new SaveDataErrorDto();
									errorDto.setErrorMsg("指标值表达式错误：" + zhibiaoValue);
									errorDto.setProperty(getErrorProperties(dataDto));
									errorList.add(errorDto);
									continue;
								}
								dlZbValueObjectMap.put("编码", zhibiaoValue);
								dlZbValueObjectMap.put("名称", zhibiaoValue);
								dlZbValueObjectMap.put("值性质", "定量");
							}
							if ("单位".equals(zhibiaoFlag)) {
								dlZbValueObjectMap.put("单位", zhibiaoValue);
							}

							if ("结果".equals(zhibiaoFlag)) {
								String[] zbValueArray = zhibiaoValue.split("、");
								for (String zbValue : zbValueArray) {
									Map<String, Object> zhibiaoValueObjectMap = new HashMap<>();
									zhibiaoValueObjectMap.put("编码", zbValue);
									zhibiaoValueObjectMap.put("名称", zbValue);
									zhibiaoValueObjectMap.put("值性质", "定性");
									dxZbValueObjectMap.add(zhibiaoValueObjectMap);
								}
							}
						}

						//定量
						if (CollUtil.isNotEmpty(dlZbValueObjectMap)) {
							Vertex vertex_zbValue = getVertex(graph, "指标值", dlZbValueObjectMap);
							log.info("指标值顶点：" + vertex_zbValue.id());
							//添加指标顶点
							Vertex vertex_zb = getVertex(graph, "指标", zhibiaoObjectMap);
							log.info("指标顶点：" + vertex_zb.id());
							//添加检验_指标边
							Edge property = new Edge("检查_指标").source(vertex_check).target(vertex_zb).property("关联疾病", vertex_diag.property("名称"));
							graph.addEdge(property);
							log.info("检查_指标关系：" + vertex_check.id() + "->" + vertex_zb.id());
							//添加检验_指标值边
							Edge edge = new Edge("检查_指标值").source(vertex_zb).target(vertex_zbValue).property("关联疾病", vertex_diag.property("名称"));
							if (StrUtil.isNotBlank(vertex_zbValue_weight)) {
								edge.property("权重", vertex_zbValue_weight);
							}
							graph.addEdge(edge);
							log.info("检查_指标值关系：" + vertex_zb.id() + "->" + vertex_zbValue.id());
						}

						//定性
						if (CollUtil.isNotEmpty(dxZbValueObjectMap)) {
							for (Map<String, Object> dxZbValueObjectMap1 : dxZbValueObjectMap) {
								Vertex vertex_zbValue = getVertex(graph, "指标值", dxZbValueObjectMap1);
								log.info("指标值顶点：" + vertex_zbValue.id());
								//添加指标顶点
								Vertex vertex_zb = getVertex(graph, "指标", zhibiaoObjectMap);
								log.info("指标顶点：" + vertex_zb.id());
								//添加检验_指标边
								Edge property = new Edge("检查_指标").source(vertex_check).target(vertex_zb).property("关联疾病", vertex_diag.property("名称"));
								graph.addEdge(property);
								log.info("检查_指标关系：" + vertex_check.id() + "->" + vertex_zb.id());
								//添加检验_指标值边
								Edge edge = new Edge("检查_指标值").source(vertex_zb).target(vertex_zbValue).property("关联疾病", vertex_diag.property("名称"));
								if (StrUtil.isNotBlank(vertex_zbValue_weight)) {
									edge.property("权重", vertex_zbValue_weight);
								}
								graph.addEdge(edge);
								log.info("检查_指标值关系：" + vertex_zb.id() + "->" + vertex_zbValue.id());
							}
						}

					}
				}

				if ("合理性审查".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> hlMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = hlMap.entrySet();
					//保存合理性因子值
					saveHlValue(graph, vertex_diag, errorList, entries2, vertex_check);
				}

				if ("影像所见".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					for (DataDto dataDto : value1) {
						if (StrUtil.isBlank(dataDto.getPropertyValue2()) || "无".equals(dataDto.getPropertyValue2())) {
							continue;
						}
						Map<String, Object> imagDiagnObjectMap = new HashMap<>();
						imagDiagnObjectMap.put("名称", dataDto.getPropertyValue2());
						imagDiagnObjectMap.put("编码", dataDto.getPropertyValue2());
						imagDiagnObjectMap.put("值性质", "定性");
						//影像所见顶点
						Vertex vertex_img = getVertex(graph, "影像所见", imagDiagnObjectMap);
						//检查_影像所见边
						Edge checkEdge = new Edge("检查_影像所见").source(vertex_check).target(vertex_img).property("关联疾病", vertex_diag.property("名称"));
						String weight = dataDto.getPropertyValue3();
						if (StrUtil.isNotBlank(weight)) {
							checkEdge.property("权重", weight);
						}
						graph.addEdge(checkEdge);
					}
				}

				if ("结论".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					for (DataDto dataDto : value1) {
						if (StrUtil.isBlank(dataDto.getPropertyValue2()) || "无".equals(dataDto.getPropertyValue2())) {
							continue;
						}
						Map<String, Object> imgConclusionMap = new HashMap<>();
						imgConclusionMap.put("名称", dataDto.getPropertyValue2());
						imgConclusionMap.put("编码", dataDto.getPropertyValue2());
						imgConclusionMap.put("值性质", "定性");
						//影像所见顶点
						Vertex vertex_imgConclusion = getVertex(graph, "影像结论", imgConclusionMap);
						//检查_影像结论边
						Edge checkerEdge = new Edge("检查_影像结论").source(vertex_check).target(vertex_imgConclusion).property("关联疾病", vertex_diag.property("名称"));
						String weight = dataDto.getPropertyValue3();
						if (StrUtil.isNotBlank(weight)) {
							checkerEdge.property("权重", weight);
						}
						graph.addEdge(checkerEdge);
					}
				}
			}
		}
	}

	/**
	 * 保存检验数据
	 *
	 * @param verify
	 * @param graph
	 * @param vertex_diag
	 * @param errorList
	 */
	private void saveVerifyData(List<DataDto> verify, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> verifuyMap = verify.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));

		Set<Map.Entry<String, List<DataDto>>> entries = verifuyMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : entries) {
			String key = entry.getKey();
			if (StrUtil.isBlank(key) || "无".equals(key)) {
				continue;
			}
			String[] split = key.split("#");
			key = split[0];
			Map<String, Object> verifyObjectMap = new HashMap<>();
			verifyObjectMap.put("编码", key);
			verifyObjectMap.put("名称", key);
			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			Set<Map.Entry<String, List<DataDto>>> entries1 = collect.entrySet();
			for (Map.Entry<String, List<DataDto>> entry1 : entries1) {
				String key1 = entry1.getKey();
				if ("注意事项".equals(key1)) {
					String collect1 = entry1.getValue().stream().map(DataDto::getPropertyValue2).filter(Objects::nonNull)
							.filter(item -> !"无".equals(item)).collect(Collectors.joining(","));
					if (StrUtil.isNotBlank(collect1)) {
						verifyObjectMap.put("注意事项", collect1);
					}
				}
			}
			//添加检验顶点
			Vertex vertex_verify = getVertex(graph, "检验", verifyObjectMap);
			log.info("检验顶点：" + vertex_verify.id());
			//添加疾病_治疗_检验关系
			if (split.length > 1) {
				vertex_diag.addEdge("疾病_治疗_检验", vertex_verify).property("聚类分组", split[1]);
			} else {
				vertex_diag.addEdge("疾病_治疗_检验", vertex_verify);
			}
			log.info("疾病_治疗_检验关系：" + vertex_diag.property("名称") + "->" + vertex_verify.id());
			for (Map.Entry<String, List<DataDto>> entry1 : entries1) {
				String key1 = entry1.getKey();
				if ("异常指标".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> zbMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = zbMap.entrySet();
					for (Map.Entry<String, List<DataDto>> entry2 : entries2) {
						String zbName = entry2.getKey();
						//指标顶点
						Map<String, Object> zhibiaoObjectMap = new HashMap<>();
						zhibiaoObjectMap.put("编码", zbName);
						zhibiaoObjectMap.put("名称", zbName);
						List<DataDto> value2 = entry2.getValue();
						//指标值顶点

						String vertex_zbValue_weight = "";
						Map<String, Object> dlZbValueObjectMap = new HashMap<>();
						List<Map<String, Object>> dxZbValueObjectMap = new ArrayList<>();
						for (DataDto dataDto : value2) {
							vertex_zbValue_weight = dataDto.getPropertyValue5();
							String zhibiaoFlag = dataDto.getPropertyValue3();
							String zhibiaoValue = dataDto.getPropertyValue4();
							if (StrUtil.isBlank(zhibiaoValue) || "无".equals(zhibiaoValue)) {
								continue;
							}
							if ("运算符".equals(zhibiaoFlag)) {
								//校验定量值
								try {
									JexlBuilder jexlBuilder = new JexlBuilder();
									JexlEngine jexlEngine = jexlBuilder.create();
									jexlEngine.createExpression(zhibiaoValue);
								} catch (Exception e) {
									SaveDataErrorDto errorDto = new SaveDataErrorDto();
									errorDto.setErrorMsg("指标值表达式错误：" + zhibiaoValue);
									errorDto.setProperty(getErrorProperties(dataDto));
									errorList.add(errorDto);
									continue;
								}
								dlZbValueObjectMap.put("编码", zhibiaoValue);
								dlZbValueObjectMap.put("名称", zhibiaoValue);
								dlZbValueObjectMap.put("值性质", "定量");
							}
							if ("单位".equals(zhibiaoFlag)) {
								dlZbValueObjectMap.put("单位", zhibiaoValue);
							}

							if ("结果".equals(zhibiaoFlag)) {
								String[] zbValueArray = zhibiaoValue.split("、");
								for (String zbValue : zbValueArray) {
									Map<String, Object> zhibiaoValueObjectMap = new HashMap<>();
									zhibiaoValueObjectMap.put("编码", zbValue);
									zhibiaoValueObjectMap.put("名称", zbValue);
									zhibiaoValueObjectMap.put("值性质", "定性");
									dxZbValueObjectMap.add(zhibiaoValueObjectMap);
								}
							}
						}

						//定量
						if (CollUtil.isNotEmpty(dlZbValueObjectMap)) {
							Vertex vertex_zbValue = getVertex(graph, "指标值", dlZbValueObjectMap);
							log.info("指标值顶点：" + vertex_zbValue.id());
							//添加指标顶点
							Vertex vertex_zb = getVertex(graph, "指标", zhibiaoObjectMap);
							log.info("指标顶点：" + vertex_zb.id());
							//添加检验_指标边
							Edge property = new Edge("检验_指标").source(vertex_verify).target(vertex_zb).property("关联疾病", vertex_diag.property("名称"));
							graph.addEdge(property);
							log.info("检验_指标关系：" + vertex_verify.id() + "->" + vertex_zb.id());
							//添加检验_指标值边
							Edge edge = new Edge("检验_指标值").source(vertex_zb).target(vertex_zbValue).property("关联疾病", vertex_diag.property("名称"));
							if (StrUtil.isNotBlank(vertex_zbValue_weight)) {
								edge.property("权重", vertex_zbValue_weight);
							}
							graph.addEdge(edge);
							log.info("检验_指标值关系：" + vertex_zb.id() + "->" + vertex_zbValue.id());
						}

						//定性
						if (CollUtil.isNotEmpty(dxZbValueObjectMap)) {
							for (Map<String, Object> dxZbValueObjectMap1 : dxZbValueObjectMap) {
								Vertex vertex_zbValue = getVertex(graph, "指标值", dxZbValueObjectMap1);
								log.info("指标值顶点：" + vertex_zbValue.id());
								//添加指标顶点
								Vertex vertex_zb = getVertex(graph, "指标", zhibiaoObjectMap);
								log.info("指标顶点：" + vertex_zb.id());
								//添加检验_指标边
								Edge property = new Edge("检验_指标").source(vertex_verify).target(vertex_zb).property("关联疾病", vertex_diag.property("名称"));
								graph.addEdge(property);
								log.info("检验_指标关系：" + vertex_verify.id() + "->" + vertex_zb.id());
								//添加检验_指标值边
								Edge edge = new Edge("检验_指标值").source(vertex_zb).target(vertex_zbValue).property("关联疾病", vertex_diag.property("名称"));
								if (StrUtil.isNotBlank(vertex_zbValue_weight)) {
									edge.property("权重", vertex_zbValue_weight);
								}
								graph.addEdge(edge);
								log.info("检验_指标值关系：" + vertex_zb.id() + "->" + vertex_zbValue.id());
							}
						}
					}
				}

				if ("合理性审查".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					Map<String, List<DataDto>> hlMap = value1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
					Set<Map.Entry<String, List<DataDto>>> entries2 = hlMap.entrySet();

					//保存合理性因子值
					saveHlValue(graph, vertex_diag, errorList, entries2, vertex_verify);
				}
			}
		}
	}

	private void saveHlValue(GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList, Set<Map.Entry<String, List<DataDto>>> entries2, Vertex vertex_verify) {
		//合理性因子值顶点
		Map<String, Object> dxhlValueObjectMap = new HashMap<>();
		Map<String, Object> dlhlValueObjectMap = new HashMap<>();

		for (Map.Entry<String, List<DataDto>> entry2 : entries2) {
			String hlName = entry2.getKey();
			//合理性因子顶点
			Map<String, Object> hlObjectMap = new HashMap<>();
			hlObjectMap.put("编码", hlName);
			hlObjectMap.put("名称", hlName);
			List<DataDto> value2 = entry2.getValue();
			//合理性因子值顶点
			if ("性别限制".equals(hlName) || "险种限制".equals(hlName)) {
				DataDto dto = value2.get(0);
				String hlValue = dto.getPropertyValue3();
				if (StrUtil.isBlank(hlValue) || "无".equals(hlValue)) {
					continue;
				}
				dxhlValueObjectMap.put("名称", hlValue);
				dxhlValueObjectMap.put("编码", hlValue);
				dxhlValueObjectMap.put("值性质", "定性");
				if (CollUtil.isNotEmpty(dxhlValueObjectMap)) {
					//添加合理性因子值顶点（定性）
					Vertex vertex_hlValue = getVertex(graph, "合理性因子值", dxhlValueObjectMap);
					log.info("指标值顶点：" + vertex_hlValue.id());
					//添加合理性因子顶点
					Vertex vertex_hl = getVertex(graph, "合理性因子", hlObjectMap);
					log.info("合理性因子顶点：" + vertex_hl.id());
					//添加检验_合理性_因子边
					Edge property = new Edge("检验_合理性_因子").source(vertex_verify).target(vertex_hl)
							.property("关联疾病", vertex_diag.property("名称"));
					graph.addEdge(property);
					log.info("检验_合理性_因子关系：" + vertex_verify.id() + "->" + vertex_hl.id());
					//添加因子_值边
					Edge edge = new Edge("合理性因子_值").source(vertex_hl).target(vertex_hlValue)
							.property("关联疾病", vertex_diag.property("名称"));
					graph.addEdge(edge);
					log.info("合理性因子_值关系：" + vertex_hl.id() + "->" + vertex_hlValue.id());
				}
			}

			if ("年龄限制".equals(hlName)) {
				for (DataDto dataDto : value2) {
					String zhibiaoFlag = dataDto.getPropertyValue3();
					String zhibiaoValue = dataDto.getPropertyValue4();
					if (StrUtil.isBlank(zhibiaoValue) || "无".equals(zhibiaoValue)) {
						break;
					}
					if ("运算符".equals(zhibiaoFlag)) {
						//校验定量值
						try {
							JexlBuilder jexlBuilder = new JexlBuilder();
							JexlEngine jexlEngine = jexlBuilder.create();
							jexlEngine.createExpression(zhibiaoValue);
						} catch (Exception e) {
							SaveDataErrorDto errorDto = new SaveDataErrorDto();
							errorDto.setErrorMsg("指标值表达式错误：" + zhibiaoValue);
							errorDto.setProperty(getErrorProperties(dataDto));
							errorList.add(errorDto);
							continue;
						}
						dlhlValueObjectMap.put("编码", zhibiaoValue);
						dlhlValueObjectMap.put("名称", zhibiaoValue);
						dlhlValueObjectMap.put("值性质", "定量");

					}
					if ("单位".equals(zhibiaoFlag)) {
						Boolean b = wmkgDictService.existInDict("年龄限制单位", zhibiaoValue);
						if (!b) {
							SaveDataErrorDto errorDto = new SaveDataErrorDto();
							errorDto.setErrorMsg("年龄限制单位值不在枚举值范围内：" + zhibiaoValue);
							errorDto.setProperty(getErrorProperties(dataDto));
							errorList.add(errorDto);
							continue;
						}
						dlhlValueObjectMap.put("单位", zhibiaoValue);
					}
				}

				if (CollUtil.isNotEmpty(dlhlValueObjectMap)) {
					//添加合理性因子值顶点（定量）
					Vertex vertex_hlValue = getVertex(graph, "合理性因子值", dlhlValueObjectMap);
					//添加合理性因子顶点
					Vertex vertex_hl = getVertex(graph, "合理性因子", hlObjectMap);
					log.info("合理性因子顶点：" + vertex_hl.id());
					//添加检验_合理性_因子边
					Edge property = new Edge("检验_合理性_因子").source(vertex_verify).target(vertex_hl)
							.property("关联疾病", vertex_diag.property("名称"));
					graph.addEdge(property);
					log.info("检验_合理性_因子关系：" + vertex_verify.id() + "->" + vertex_hl.id());
					//添加因子_值边
					Edge edge = new Edge("合理性因子_值").source(vertex_hl).target(vertex_hlValue)
							.property("关联疾病", vertex_diag.property("名称"));
					graph.addEdge(edge);
					log.info("合理性因子_值关系：" + vertex_hl.id() + "->" + vertex_hlValue.id());
				}
			}
		}
	}

	/**
	 * 保存多发因素值
	 *
	 * @param multiple
	 * @param graph
	 * @param vertex_diag
	 * @param errorList
	 */
	private void saveMultipleData(List<DataDto> multiple, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> multipleMap = multiple.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		Set<Map.Entry<String, List<DataDto>>> entries4 = multipleMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : entries4) {
			String multipleName = entry.getKey();
			String[] multipleNameArr = multipleName.split("#");
			multipleName = multipleNameArr[0];
			Map<String, Object> multipleMapJb = new HashMap<>();
			multipleMapJb.put("编码", multipleName);
			multipleMapJb.put("名称", multipleName);

			List<DataDto> value = entry.getValue();
			if (CollUtil.isNotEmpty(value)) {
				DataDto dto = value.get(0);
				String propertyValue1 = dto.getPropertyValue1();
				if (StrUtil.isBlank(propertyValue1) || "无".equals(propertyValue1)) {
					continue;
				}

				String[] propertyValues = propertyValue1.split("、");
				for (String propertyValue : propertyValues) {
					if (!"手术".equals(multipleName)){
						Boolean existInDict = wmkgDictService.existInDict(multipleName, propertyValue);
						if (!existInDict) {
							SaveDataErrorDto errorDto = new SaveDataErrorDto();
							errorDto.setErrorMsg(multipleName+"值不在枚举值范围内：" + propertyValue);
							errorDto.setProperty(getErrorProperties(dto));
							errorList.add(errorDto);
							continue;
						}
					}
					Map<String, Object> multipleValueMapJb = new HashMap<>();
					multipleValueMapJb.put("编码", propertyValue);
					multipleValueMapJb.put("名称", propertyValue);
					multipleValueMapJb.put("值性质", "定性");
					Vertex vertex_multiple_value = getVertex(graph, "多发因素值", multipleValueMapJb);
					log.info("多发因素值顶点：" + vertex_multiple_value.id());

					Vertex vertex_multiple = getVertex(graph, "多发因素", multipleMapJb);
					log.info("多发因素顶点：" + vertex_multiple.id());
					//添加疾病_多发因素关系
					if (multipleNameArr.length > 1) {
						vertex_diag.addEdge("疾病_多发因素", vertex_multiple).property("诊断分组", multipleNameArr[1]);
					} else {
						vertex_diag.addEdge("疾病_多发因素", vertex_multiple);
					}
					log.info("疾病_多发因素关系：" + vertex_diag.property("名称") + "->" + vertex_multiple.id());

					//添加多发因素_值关系
					Edge multipleEdge = new Edge("多发因素_值").source(vertex_multiple)
							.target(vertex_multiple_value).property("关联疾病", vertex_diag.property("名称"));
					String weight = dto.getPropertyValue2();
					multipleEdge.property("关联疾病", vertex_diag.property("名称"));
					if (StrUtil.isNotBlank(weight)) {
						Boolean b = wmkgDictService.existInDict("依据", weight);
						if (!b) {
							SaveDataErrorDto errorDto = new SaveDataErrorDto();
							errorDto.setErrorMsg("权重值不在枚举值范围内：" + weight);
							errorDto.setProperty(getErrorProperties(dto));
							errorList.add(errorDto);
							continue;
						}
						multipleEdge.property("权重", weight);
					}
					graph.addEdge(multipleEdge);
					log.info("多发因素_值关系：" + vertex_multiple.id() + "->" + vertex_multiple_value.id());
				}
			}
		}
	}

	/**
	 * 保存疾病_临床表现_症状关系
	 *
	 * @param smpList
	 * @param graph
	 * @param vertex_diag
	 * @param errorList
	 */
	private void saveSymptomData(List<DataDto> smpList, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> smpMap = smpList.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		for (Map.Entry<String, List<DataDto>> entry : smpMap.entrySet()) {
			String smpMapName = entry.getKey();
			Map<String, Object> smpMapJb = new HashMap<>();
			String[] split = smpMapName.split("#");
			smpMapName = split[0];
			smpMapJb.put("编码", smpMapName);
			smpMapJb.put("名称", smpMapName);
			Vertex vertex_smp = getVertex(graph, "症状", smpMapJb);
			log.info("症状顶点：" + vertex_smp.id());

			//疾病_临床表现_症状关系
			Edge sympEdge = vertex_diag.addEdge("疾病_临床表现_症状", vertex_smp);
			log.info("疾病_临床表现_症状关系：" + vertex_diag.property("名称") + "->" + vertex_smp.id());
			if (split.length > 1) {
				sympEdge.property("诊断分组", split[1]);
			}
			List<DataDto> value = entry.getValue();
			//设置症状的权重
			List<DataDto> collect1 = value.stream().filter(item -> "是否为主要症状".equals(item.getPropertyValue1())).collect(Collectors.toList());
			if (CollUtil.isNotEmpty(collect1)) {
				DataDto dataDto = collect1.get(0);
				String isMainSymptom = dataDto.getPropertyValue2();
				if (StrUtil.isNotBlank(isMainSymptom)) {
					sympEdge.property("是否主要症状", isMainSymptom);
				}
				String weight = dataDto.getPropertyValue3();
				if (StrUtil.isNotBlank(weight)) {
					Boolean b = wmkgDictService.existInDict("依据", weight);
					if (!b) {
						SaveDataErrorDto errorDto = new SaveDataErrorDto();
						errorDto.setErrorMsg("权重值不在枚举值范围内：" + weight);
						errorDto.setProperty(getErrorProperties(dataDto));
						errorList.add(errorDto);
						continue;
					}
					sympEdge.property("权重", weight);
				}
			}
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			for (Map.Entry<String, List<DataDto>> entry1 : collect.entrySet()){
				String key1 = entry1.getKey();
				if ("要素".equals(key1)) {
					List<DataDto> value1 = entry1.getValue();
					for (DataDto dataDto : value1) {
						String symptomEle = dataDto.getPropertyValue2();
						Map<String, Object> sympEleObjectMap = new HashMap<>();
						sympEleObjectMap.put("编码", symptomEle);
						sympEleObjectMap.put("名称", symptomEle);

						String sympEleValue = dataDto.getPropertyValue3();
						if (StrUtil.isBlank(sympEleValue) || "无".equals(sympEleValue)) {
							continue;
						}

						Vertex vertex_sympEle = getVertex(graph, "症状要素", sympEleObjectMap);
						log.info("症状要素顶点：" + vertex_sympEle.id());
						Edge sympEleEe = new Edge("症状_包含_要素").source(vertex_smp).target(vertex_sympEle).property("关联疾病", vertex_diag.property("名称"));
						graph.addEdge(sympEleEe);
						log.info("症状_包含_要素关系：" + vertex_smp.id() + "->" + vertex_sympEle.id());

						String[] values = sympEleValue.split("、");
						for (String itemvalue : values) {
							if (CommonConstants.symptomElement.contains(itemvalue)){
								Boolean b = wmkgDictService.existInDict(symptomEle, itemvalue);
								if (!b) {
									SaveDataErrorDto errorDto = new SaveDataErrorDto();
									errorDto.setErrorMsg(symptomEle+"值不在枚举值范围内：" + itemvalue);
									errorDto.setProperty(getErrorProperties(dataDto));
									errorList.add(errorDto);
									continue;
								}
							}
							Map<String, Object> sympEleValueMap = new HashMap<>();
							sympEleValueMap.put("编码", itemvalue);
							sympEleValueMap.put("名称", itemvalue);
							sympEleValueMap.put("值性质", "定性");
							Vertex vertex_sympEle_value = getVertex(graph, "症状要素值", sympEleValueMap);
							log.info("症状要素值顶点：" + vertex_sympEle_value.id());

							Edge sympEleEdge = new Edge("症状_要素_值").source(vertex_sympEle).target(vertex_sympEle_value)
									.property("关联疾病", vertex_diag.property("名称"))
									.property("关联症状", vertex_smp.property("名称"));
							String weight = dataDto.getPropertyValue4();
							if (StrUtil.isNotBlank(weight)) {
								Boolean b = wmkgDictService.existInDict("依据", weight);
								if (!b) {
									SaveDataErrorDto errorDto = new SaveDataErrorDto();
									errorDto.setErrorMsg("权重值不在枚举值范围内：" + weight);
									errorDto.setProperty(getErrorProperties(dataDto));
									errorList.add(errorDto);
									continue;
								}
								sympEleEdge.property("权重", weight);
							}
							graph.addEdge(sympEleEdge);
							log.info("症状_要素_值关系：" + vertex_sympEle.id() + "->" + vertex_sympEle_value.id());
						}
					}
				}
			}
		}
	}

	/**
	 * 保存体检数据
	 *
	 * @param physicalExamList
	 * @param graph
	 * @param vertex_diag
	 */
	private void savePhysicalExamData(List<DataDto> physicalExamList, GraphManager graph, Vertex vertex_diag, List<SaveDataErrorDto> errorList) {
		Map<String, List<DataDto>> physicalExamMap = physicalExamList.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		Set<Map.Entry<String, List<DataDto>>> entries3 = physicalExamMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : entries3) {
			String physicalExamName = entry.getKey();
			Map<String, Object> physicalExamMapJb = new HashMap<>();
			String[] split = physicalExamName.split("#");
			physicalExamName = split[0];
			physicalExamMapJb.put("编码", physicalExamName);
			physicalExamMapJb.put("名称", physicalExamName);
			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
			for (Map.Entry<String, List<DataDto>> physicalList : collect.entrySet()) {
				List<DataDto> value2 = physicalList.getValue();
				for (DataDto dataDto : value2) {
					String propertyValue1 = dataDto.getPropertyValue1();
					if (StrUtil.isBlank(propertyValue1) || "无".equals(propertyValue1)) {
						continue;
					}
					Map<String, Object> physicalExamValMapJb = new HashMap<>();
					physicalExamValMapJb.put("编码", propertyValue1);
					physicalExamValMapJb.put("名称", propertyValue1);
					if (CommonConstants.quantitative.contains(physicalExamName)) {
						//校验定量值
						try {
							JexlBuilder jexlBuilder = new JexlBuilder();
							JexlEngine jexlEngine = jexlBuilder.create();
							jexlEngine.createExpression(propertyValue1);
						} catch (Exception e) {
							SaveDataErrorDto errorDto = new SaveDataErrorDto();
							errorDto.setErrorMsg("指标值表达式错误：" + propertyValue1);
							errorDto.setProperty(getErrorProperties(dataDto));
							errorList.add(errorDto);
							continue;
						}
						physicalExamValMapJb.put("值性质", "定量");
					} else {
						physicalExamValMapJb.put("值性质", "定性");
					}
					Vertex vertex_physicalExamVal = getVertex(graph, "体格检查值", physicalExamValMapJb);
					log.info("体格检查值顶点：" + vertex_physicalExamVal.id());

					Vertex vertex_physicalExam = getVertex(graph, "体格检查", physicalExamMapJb);
					log.info("体格检查顶点：" + vertex_physicalExam.id());
					//疾病_体格检查关系
					Edge diagExamEdge = null;
					if (split.length > 1) {
						diagExamEdge = new Edge("疾病_体格检查").source(vertex_diag).target(vertex_physicalExam)
								.property("诊断分组", split[1]);
					} else {
						diagExamEdge = new Edge("疾病_体格检查").source(vertex_diag).target(vertex_physicalExam);
					}
					graph.addEdge(diagExamEdge);
					log.info("疾病_体格检查关系：" + vertex_diag.property("名称") + "->" + vertex_physicalExam.id());

					//体格检查_值关系
					Edge physicalExamEdge = new Edge("体格检查_值").source(vertex_physicalExam).target(vertex_physicalExamVal).property("关联疾病", vertex_diag.property("名称"));
					String weight = dataDto.getPropertyValue2();
					if (StrUtil.isNotBlank(weight)) {
						Boolean b = wmkgDictService.existInDict("依据", weight);
						if (!b) {
							SaveDataErrorDto errorDto = new SaveDataErrorDto();
							errorDto.setErrorMsg("权重值不在枚举值范围内：" + weight);
							errorDto.setProperty(getErrorProperties(dataDto));
							errorList.add(errorDto);
							continue;
						}
						physicalExamEdge.property("权重", weight);
					}
					graph.addEdge(physicalExamEdge);
					log.info("体格检查_值关系：" + vertex_physicalExam.id() + "->" + vertex_physicalExamVal.id());
				}
			}
		}
	}

	public String getErrorProperties(DataDto dataDto) {
		StringBuilder errorProperties = new StringBuilder();
		errorProperties.append(dataDto.getProperty());
		if (StrUtil.isNotBlank(dataDto.getPropertyValue())) {
			errorProperties.append("-").append(dataDto.getPropertyValue());
		}
		if (StrUtil.isNotBlank(dataDto.getPropertyValue1())) {
			errorProperties.append("-").append(dataDto.getPropertyValue1());
		}
		if (StrUtil.isNotBlank(dataDto.getPropertyValue2())) {
			errorProperties.append("-").append(dataDto.getPropertyValue2());
		}
		if (StrUtil.isNotBlank(dataDto.getPropertyValue3())) {
			errorProperties.append("-").append(dataDto.getPropertyValue3());
		}
		if (StrUtil.isNotBlank(dataDto.getPropertyValue4())) {
			errorProperties.append("-").append(dataDto.getPropertyValue4());
		}
		return errorProperties.toString();
	}

	@NotNull
	private Vertex getVertexDiag(List<DataDto> schemaDtoList, GraphManager graph, List<SaveDataErrorDto> errorList) {
		//疾病顶点数据
		Map<String, Object> diagObjectMap = new HashMap<>();

		//icd顶点数据
		Map<String, Object> idcObjectMap = new HashMap<>();

		//医保顶点数据
		Map<String, Object> yibaoObjectMap = new HashMap<>();

		//科室顶点数据
		Map<String, Object> deptObjectMap = new HashMap<>();

		//中心诊断数据
		Map<String, Object> centralDiagObjectMap = new HashMap<>();

		//别名顶点数据
		List<Map<String, Object>> aliasObjectMapList = new ArrayList<>();

		for (DataDto dataDto : schemaDtoList) {
			//疾病顶点数据
			String property = dataDto.getProperty();
			if ("疾病编码".equals(property)) {
				String value = dataDto.getPropertyValue();
				diagObjectMap.put("编码", value);

				idcObjectMap.put("编码", value);
				idcObjectMap.put("名称", value);
			}
			if ("疾病名称".equals(property)) {
				String value = dataDto.getPropertyValue();
				diagObjectMap.put("名称", value);
			}
			if ("是否危重症".equals(property)) {
				String value = dataDto.getPropertyValue();
				Boolean b = wmkgDictService.existInDict(property, value);
				if (!b) {
					SaveDataErrorDto errorDto = new SaveDataErrorDto();
					errorDto.setErrorMsg("是否危重症不在枚举值范围内：" + value);
					errorDto.setProperty(getErrorProperties(dataDto));
					errorList.add(errorDto);
					continue;
				}
				diagObjectMap.put("危重标志", value);
			}

			//医保顶点数据
			if ("医保疾病编码".equals(property)) {
				String value = dataDto.getPropertyValue();
				yibaoObjectMap.put("编码", value);
				yibaoObjectMap.put("名称", value);
			}

			//科室顶点数据
			if ("所属科室".equals(property)) {
				String value = dataDto.getPropertyValue();
				deptObjectMap.put("编码", value);
				deptObjectMap.put("名称", value);
			}

			//别名顶点数据
			if ("别名".equals(property)) {
				String value = dataDto.getPropertyValue();
				if (StrUtil.isNotBlank(value)
						&& !"无".equals(value)) {
					Map<String, Object> aliasObjectMap = new HashMap<>();
					aliasObjectMap.put("编码", value);
					aliasObjectMap.put("名称", value);
					aliasObjectMapList.add(aliasObjectMap);
				}
			}

			//中心诊断顶点数据
			if ("中心诊断".equals(property)) {
				String value = dataDto.getPropertyValue();
				if (StrUtil.isBlank(value)) {
					continue;
				}
				if (("无").equals(value)) {
					continue;
				}
				if (value.startsWith("无")) {
					continue;
				}
				WmkgDiseaseDict diseaseDict = wmkgDiseaseDictService.getDiseaseDict(value);
				if (Objects.isNull(diseaseDict)) {
					SaveDataErrorDto errorDto = new SaveDataErrorDto();
					errorDto.setErrorMsg("中心诊断不在疾病字典库内：" + value);
					errorDto.setProperty(getErrorProperties(dataDto));
					errorList.add(errorDto);
					continue;
				}
				centralDiagObjectMap.put("编码", diseaseDict.getDiseaseCode());
				centralDiagObjectMap.put("名称", value);
			}

			//设置多发因素、症状、体格检查、检验检查的占比值
			if (property.contains("多发因素#")) {
				String[] split = property.split("#");
				if (StrUtil.isNotBlank(split[1])) {
					diagObjectMap.put("多发因素占分比", split[1]);
				}
			}

			if (property.contains("症状#")) {
				String[] split = property.split("#");
				if (StrUtil.isNotBlank(split[1])) {
					diagObjectMap.put("症状占分比", split[1]);
				}
			}

			if (property.contains("体格检查#")) {
				String[] split = property.split("#");
				if (StrUtil.isNotBlank(split[1])) {
					diagObjectMap.put("体格检查占分比", split[1]);
				}
			}

			if (property.contains("检验项目#")) {
				String[] split = property.split("#");
				if (StrUtil.isNotBlank(split[1])) {
					diagObjectMap.put("检验占分比", split[1]);
				}
			}

			if (property.contains("检查项目#")) {
				String[] split = property.split("#");
				if (StrUtil.isNotBlank(split[1])) {
					diagObjectMap.put("检查占分比", split[1]);
				}
			}
		}

		//疾病顶点
		Vertex vertex_diag = getVertex(graph, "疾病", diagObjectMap);
		log.info("疾病顶点：" + vertex_diag.property("名称"));

		//icd顶点
		Vertex vertex_icd = getVertex(graph, "ICD编码", idcObjectMap);
		log.info("icd顶点：" + vertex_icd.id());

		//疾病对应icd顶点的边关系
		vertex_diag.addEdge("ICD对应", vertex_icd);
		log.info("疾病对应icd顶点的边关系：" + vertex_diag.property("名称") + "->" + vertex_icd.id());

		//医保顶点
		Vertex vertex_yibao = getVertex(graph, "医保编码", yibaoObjectMap);
		log.info("医保顶点：" + vertex_yibao.id());

		//疾病对应医保顶点的边关系
		vertex_diag.addEdge("医保对应", vertex_yibao);
		log.info("疾病对应医保顶点的边关系：" + vertex_diag.property("名称") + "->" + vertex_yibao.id());

		//科室顶点
		Vertex vertex_dept = getVertex(graph, "科室", deptObjectMap);
		log.info("科室顶点：" + vertex_dept.id());

		//疾病对应科室顶点的边关系
		vertex_diag.addEdge("所属科室", vertex_dept);
		log.info("疾病对应科室顶点的边关系：" + vertex_diag.property("名称") + "->" + vertex_dept.id());

		for (Map<String, Object> item : aliasObjectMapList) {
			Vertex vertex_alias = getVertex(graph, "别名", item);
			log.info("别名顶点：" + vertex_alias.id());
			//疾病_别名关系
			vertex_diag.addEdge("疾病_别名", vertex_alias);
			log.info("疾病_别名关系：" + vertex_diag.property("名称") + "->" + vertex_alias.id());
		}

		if (CollUtil.isNotEmpty(centralDiagObjectMap)) {
			Vertex vertex_centralDiag = getVertex(graph, "疾病", centralDiagObjectMap);
			log.info("疾病顶点：" + vertex_centralDiag.id());
			//疾病_别名关系
			vertex_diag.addEdge("疾病_中心诊断_疾病", vertex_centralDiag);
			log.info("疾病_中心诊断_疾病关系：" + vertex_diag.property("名称") + "->" + vertex_centralDiag.id());
		}
		return vertex_diag;
	}

	@Override
	public List<SchemaEdgeErrorDto> batchSaveDrugCategory(List<DrugCategoryDto> drugCategoryList) {

		log.info("药品分类数据初始化开始");
		HugeClient hugeClient = HugeClient.builder("http://192.168.60.140:8082", "zhytest")
				.build();
		GraphManager graph = hugeClient.graph();

		Map<String, List<DrugCategoryDto>> firstMap = drugCategoryList.stream().collect(Collectors.groupingBy(DrugCategoryDto::getCode1));
		Set<Map.Entry<String, List<DrugCategoryDto>>> entries = firstMap.entrySet();
		for (Map.Entry<String, List<DrugCategoryDto>> entry : entries) {
			String key = entry.getKey();
			List<DrugCategoryDto> value = entry.getValue();
			String code1Name = value.get(0).getCode1Name();

			//药品分类顶点
			Map<String, Object> drugCateObjectMap = new HashMap<>();
			drugCateObjectMap.put("编码", key);
			drugCateObjectMap.put("名称", code1Name);
			Vertex vertex_drugCate = getVertex(graph, "药品分类", drugCateObjectMap);
			log.info("一级药品分类顶点：" + vertex_drugCate.id());

			Map<String, List<DrugCategoryDto>> secondMap = value.stream().collect(Collectors.groupingBy(DrugCategoryDto::getCode2));
			Set<Map.Entry<String, List<DrugCategoryDto>>> secondEntries = secondMap.entrySet();
			for (Map.Entry<String, List<DrugCategoryDto>> entry1 : secondEntries) {
				String key1 = entry1.getKey();
				List<DrugCategoryDto> value1 = entry1.getValue();
				String code2Name = value1.get(0).getCode2Name();
				if (StrUtil.isBlank(key1)) {
					continue;
				}
				if (StrUtil.isBlank(code2Name)) {
					continue;
				}
				Map<String, Object> drugCateObjectMap1 = new HashMap<>();
				drugCateObjectMap1.put("编码", key1);
				drugCateObjectMap1.put("名称", code2Name);
				Vertex vertex_drugCate1 = getVertex(graph, "药品分类", drugCateObjectMap1);
				log.info("二级药品分类顶点：" + vertex_drugCate1.id());
				if (Objects.nonNull(vertex_drugCate1)) {
					//添加药品分类_子分类边
					vertex_drugCate1.addEdge("上级药品分类", vertex_drugCate);
					log.info("上级药品分类关系：" + vertex_drugCate.id() + "->" + vertex_drugCate1.id());
					Map<String, List<DrugCategoryDto>> thirdMap = value1.stream().collect(Collectors.groupingBy(DrugCategoryDto::getCode3));

					Set<Map.Entry<String, List<DrugCategoryDto>>> thirdEntries = thirdMap.entrySet();
					for (Map.Entry<String, List<DrugCategoryDto>> entry2 : thirdEntries) {
						String key2 = entry2.getKey();
						List<DrugCategoryDto> value2 = entry2.getValue();
						String code3Name = value2.get(0).getCode3Name();
						if (StrUtil.isBlank(key2)) {
							continue;
						}
						if (StrUtil.isBlank(code3Name)) {
							continue;
						}
						Map<String, Object> drugCateObjectMap2 = new HashMap<>();
						drugCateObjectMap2.put("编码", key2);
						drugCateObjectMap2.put("名称", code3Name);
						Vertex vertex_drugCate2 = getVertex(graph, "药品分类", drugCateObjectMap2);
						log.info("三级药品分类顶点：" + vertex_drugCate2.id());

						vertex_drugCate2.addEdge("上级药品分类", vertex_drugCate1);
						log.info("上级药品分类关系：" + vertex_drugCate2.id() + "->" + vertex_drugCate1.id());
						Map<String, List<DrugCategoryDto>> collect = value2.stream().collect(Collectors.groupingBy(DrugCategoryDto::getCode3));
						for (Map.Entry<String, List<DrugCategoryDto>> entry3 : collect.entrySet()) {
							String key3 = entry3.getKey();
							List<DrugCategoryDto> value3 = entry3.getValue();
							if (StrUtil.isBlank(key3)) {
								continue;
							}
							String code4Name = value3.get(0).getCode4Name();
							if (StrUtil.isBlank(code4Name)) {
								continue;
							}
							Map<String, Object> drugCateObjectMap3 = new HashMap<>();
							drugCateObjectMap3.put("编码", key3);
							drugCateObjectMap3.put("名称", code4Name);
							Vertex vertex_drugCate3 = getVertex(graph, "药品分类", drugCateObjectMap3);
							log.info("四级药品分类顶点：" + vertex_drugCate3.id());
							vertex_drugCate3.addEdge("上级药品分类", vertex_drugCate2);
							log.info("上级药品分类关系：" + vertex_drugCate3.id() + "->" + vertex_drugCate2.id());
						}
					}
				}
			}
		}


		return null;
	}

	@Override
	public List<HealthDrugErrorDto> healthInsuranceDrug(List<HealthDrugDto> schemaDtoList) {
		HugeClient hugeClient = HugeClient.builder("http://192.168.60.140:8082", "mipkb")
				.build();
		GraphManager graph = hugeClient.graph();
		SchemaManager schema = hugeClient.schema();

		//创建顶点
		String[] properties = {"名称", "主键", "编码"};

		//边属性
		for (String prop : properties) {
			schema.propertyKey(prop).asText().ifNotExist().create();
		}

		String[] nullProperties = {"主键", "编码"};
		String[] pProperties = {"名称"};
		List<String> vertexList = schemaDtoList.stream().map(HealthDrugDto::getEntityType).distinct().collect(Collectors.toList());
		for (String vertex : vertexList) {
			schema.vertexLabel(vertex)
					.properties(properties)
					.nullableKeys(nullProperties)
					.primaryKeys(pProperties)
					.ifNotExist()
					.create();
		}
		List<String> vertexList1 = schemaDtoList.stream().map(HealthDrugDto::getValueType).distinct().collect(Collectors.toList());
		for (String vertex : vertexList1) {
			schema.vertexLabel(vertex)
					.properties(properties)
					.nullableKeys(nullProperties)
					.primaryKeys(pProperties)
					.ifNotExist()
					.create();
		}

		//创建边
		Set<Triple<String, String, String>> edgeList = schemaDtoList.stream().map(schemaDto -> Triple.of(schemaDto.getEntityType(), schemaDto.getProperty(), schemaDto.getValueType())).collect(Collectors.toSet());
		for (Triple<String, String, String> triple : edgeList) {
			String label = triple.getLeft() + "_" + triple.getMiddle() + "_" + triple.getRight();
			schema.edgeLabel(label)
					.sourceLabel(triple.getLeft())
					.targetLabel(triple.getRight())
					.ifNotExist()
					.create();
		}

		//添加数据
		for (HealthDrugDto schemaDto : schemaDtoList) {

			String entityType = schemaDto.getEntityType();
			Map<String, Object> hlValueObjectMap = new HashMap<>();
			hlValueObjectMap.put("名称", schemaDto.getEntity());
			hlValueObjectMap.put("主键", schemaDto.getEntityId());
			if (StrUtil.isNotBlank(schemaDto.getEntitySourceCode())) {
				hlValueObjectMap.put("编码", schemaDto.getEntitySourceCode());
			}
			//添加顶点数据
			Vertex vertex = getVertex(graph, entityType, hlValueObjectMap);
			log.info("顶点数据1：" + vertex.id());

			String valueType = schemaDto.getValueType();
			Map<String, Object> hlValueObjectMap1 = new HashMap<>();
			hlValueObjectMap1.put("名称", schemaDto.getValue());
			hlValueObjectMap1.put("主键", schemaDto.getValueId());
			if (StrUtil.isNotBlank(schemaDto.getValueSourceCode())) {
				hlValueObjectMap1.put("编码", schemaDto.getValueSourceCode());
			}

			//添加顶点数据
			Vertex vertex1 = getVertex(graph, valueType, hlValueObjectMap1);
			log.info("顶点数据2：" + vertex.id());

			String link = entityType + "_" + schemaDto.getProperty() + "_" + valueType;
			vertex.addEdge(link, vertex1);
			log.info(link + "|边：" + vertex.id() + "->" + vertex1.id());
		}
		return null;
	}

	@Override
	public List<ImportDataDto> getImportData(List<DataDto> schemaDtoList) {
		log.info("数据初始化开始");

		ImportDataDto importDataDto = new ImportDataDto();

		for (DataDto dataDto : schemaDtoList) {
			//疾病顶点数据
			String property = dataDto.getProperty();
			if ("疾病名称".equals(property)) {
				String value = dataDto.getPropertyValue();
				importDataDto.setDiagName(value);
				break;
			}
		}
		Map<String, List<DataDto>> rootMap = schemaDtoList.stream().collect(Collectors.groupingBy(DataDto::getProperty));

		//症状顶点
		StringBuilder symptomName = new StringBuilder();
		List<DataDto> smpList = rootMap.get("症状");
		Map<String, List<DataDto>> smpMap = smpList.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		smpMap.entrySet().stream().forEach(entry -> {
			String smpMapName = entry.getKey();
			symptomName.append(smpMapName).append("，");
		});
		importDataDto.setSymptom(symptomName.substring(0, symptomName.length() - 1));

		//检验顶点
		StringBuilder verifyName = new StringBuilder();
		List<DataDto> verify = rootMap.get("检验项目");
		Map<String, List<DataDto>> verifuyMap = verify.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		Set<Map.Entry<String, List<DataDto>>> entries = verifuyMap.entrySet();
		for (Map.Entry<String, List<DataDto>> entry : entries) {
			verifyName.append(entry.getKey()).append("，");
		}
		importDataDto.setTesting(verifyName.substring(0, verifyName.length() - 1));

		//检查顶点
		StringBuilder checkName = new StringBuilder();
		List<DataDto> check = rootMap.get("检查项目");
		Map<String, List<DataDto>> checkMap = check.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		checkMap.entrySet().forEach(entry -> {
			checkName.append(entry.getKey()).append("，");
		});
		importDataDto.setCheck(checkName.substring(0, checkName.length() - 1));

		//药品顶点
		StringBuilder drugName = new StringBuilder();
		List<DataDto> drug = rootMap.get("药品");
		List<DataDto> drug1 = drug.stream().filter(dto -> !CommonConstants.groupName.contains(dto.getPropertyValue())).collect(Collectors.toList());
		Map<String, List<DataDto>> drugCateMap = drug1.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue1));
		for (Map.Entry<String, List<DataDto>> entry : drugCateMap.entrySet()) {
			List<DataDto> value = entry.getValue();
			Map<String, List<DataDto>> collect1 = value.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue2));
			for (Map.Entry<String, List<DataDto>> entry1 : collect1.entrySet()) {
				drugName.append(entry1.getKey()).append("，");
			}
		}
		importDataDto.setDrugs(drugName.substring(0, drugName.length() - 1));

		//操作顶点
		StringBuilder operateName = new StringBuilder();
		List<DataDto> operate = rootMap.get("操作");
		Map<String, List<DataDto>> operateMap = operate.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		operateMap.entrySet().forEach(entry -> {
			operateName.append(entry.getKey()).append("，");
		});
		importDataDto.setOperator(operateName.substring(0, operateName.length() - 1));

		//手术顶点
		StringBuilder surgeryName = new StringBuilder();
		List<DataDto> surgery = rootMap.get("手术");
		Map<String, List<DataDto>> surgeryMap = surgery.stream().collect(Collectors.groupingBy(DataDto::getPropertyValue));
		for (Map.Entry<String, List<DataDto>> entry : surgeryMap.entrySet()) {
			if ("无".equals(entry.getKey())) {
				continue;
			}
			surgeryName.append(entry.getKey()).append("，");
		}
		if (StrUtil.isNotBlank(surgeryName.toString())) {
			importDataDto.setSurgery(surgeryName.substring(0, surgeryName.length() - 1));
		}
		return Collections.singletonList(importDataDto);
	}


	/**
	 * 获取顶点
	 *
	 * @param graph
	 * @param vertexName
	 * @param objectMap
	 * @return
	 */
	public Vertex getVertex(GraphManager graph, String vertexName, Map<String, Object> objectMap) {
		Vertex vertex = null;
		List<Vertex> diagVertexList = graph.listVertices(vertexName, objectMap);
		if (CollUtil.isEmpty(diagVertexList)) {
			vertex = graph.addVertex(vertexName, objectMap);
		} else {
			vertex = diagVertexList.get(0);
		}
		return vertex;
	}
}
