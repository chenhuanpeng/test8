package com.aeye.aeaimb.hugegraph.operate;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.hugegraph.config.HGFactory;
import com.aeye.aeaimb.hugegraph.controller.dto.SchemaEdge;
import com.aeye.aeaimb.hugegraph.controller.dto.SchemaEdgeDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SchemaEdgeErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SchemaVertex;
import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hugegraph.driver.SchemaManager;
import org.apache.hugegraph.structure.constant.Frequency;
import org.apache.hugegraph.structure.schema.VertexLabel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BuildGraphSchemeOp {

	private static List<String> toProperties(SchemaEdge edge) {
		List<String> properties = new ArrayList<>();
		if (StringUtils.isNotEmpty(edge.getProperty1())) {
			properties.add(edge.getProperty1());
		}
		if (StringUtils.isNotEmpty(edge.getProperty2())) {
			properties.add(edge.getProperty2());
		}
		if (StringUtils.isNotEmpty(edge.getProperty3())) {
			properties.add(edge.getProperty3());
		}
		return properties;
	}

	private static List<String> toProperties(SchemaVertex schemaDto) {
		List<String> properties = new ArrayList<>();
		if (StringUtils.isNotEmpty(schemaDto.getProperty1())) {
			properties.add(schemaDto.getProperty1());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty2())) {
			properties.add(schemaDto.getProperty2());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty3())) {
			properties.add(schemaDto.getProperty3());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty4())) {
			properties.add(schemaDto.getProperty4());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty5())) {
			properties.add(schemaDto.getProperty5());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty6())) {
			properties.add(schemaDto.getProperty6());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty7())) {
			properties.add(schemaDto.getProperty7());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty8())) {
			properties.add(schemaDto.getProperty8());
		}
		if (StringUtils.isNotEmpty(schemaDto.getProperty9())) {
			properties.add(schemaDto.getProperty9());
		}
		return properties;
	}

	public static void vertex(List<SchemaVertex> schemaDtoList) {
		SchemaManager schema = HGFactory.ins().schema();
		schemaDtoList = schemaDtoList.stream().filter(s -> StringUtils.isNotEmpty(s.getVertex())).collect(Collectors.toList());
		int i = 1;
		for (SchemaVertex schemaDto : schemaDtoList) {
			log.info("属性和顶点行数为:" + i + "条，总行数为:" + schemaDtoList.size());
			i++;
			//创建属性
			List<String> nullKeys = new ArrayList<>();
			List<String> properties = toProperties(schemaDto);
			String primaryKey = properties.get(0);
			//设置主键
			for (String prop : properties) {
				schema.propertyKey(prop).asText().ifNotExist().create();
				if (!primaryKey.equals(prop)) {
					nullKeys.add(prop);
				}
			}
			//创建顶点
			String vertex = schemaDto.getVertex();
			if ("药品用量".equals(vertex)){
				schema.vertexLabel(vertex)
						.properties(properties.toArray(new String[]{}))
						.nullableKeys(properties.toArray(new String[0]))
						.useAutomaticId()
						.ifNotExist()
						.create();
			}else {
				schema.vertexLabel(vertex)
						.properties(properties.toArray(new String[]{}))
						.nullableKeys(nullKeys.toArray(new String[0]))
						.primaryKeys(primaryKey)
						.ifNotExist()
						.create();
			}
		}
		log.info("属性和顶点初始化结束");
	}

	public static List<SchemaEdgeErrorDto> edge(List<SchemaEdge> vertexList) {
		log.info("边关系初始化开始");
		SchemaManager schema = HGFactory.ins().schema();
		int i = 1;
		List<SchemaEdgeErrorDto> errorList = new ArrayList<>();
		vertexList = vertexList.stream().filter(s -> StringUtils.isNotEmpty(s.getSourceVertex())).collect(Collectors.toList());
		for (SchemaEdge schemaEdge : vertexList) {
			log.info("边关系行数为:" + i + "条，总行数为:" + vertexList.size());
			i++;
			//判断顶点是否存在
			VertexLabel vertexLabel = schema.getVertexLabel(schemaEdge.getSourceVertex());
			if (!vertexLabel.checkExist()) {
				SchemaEdgeErrorDto errorDto = new SchemaEdgeErrorDto();
				BeanUtil.copyProperties(schemaEdge, errorDto);
				errorDto.setErrorMsg("源顶点不存在：" + schemaEdge.getSourceVertex());
				errorList.add(errorDto);
				continue;
			}
			VertexLabel vertexLabel1 = schema.getVertexLabel(schemaEdge.getTargetVertex());
			if (!vertexLabel1.checkExist()) {
				SchemaEdgeErrorDto errorDto = new SchemaEdgeErrorDto();
				BeanUtil.copyProperties(schemaEdge, errorDto);
				errorDto.setErrorMsg("目标顶点不存在：" + schemaEdge.getTargetVertex());
				errorList.add(errorDto);
				continue;
			}

			//边属性
			List<String> nullProperties = new ArrayList<>();
			List<String> linkProperties = toProperties(schemaEdge);
			for (String prop : linkProperties) {
				schema.propertyKey(prop).asText().ifNotExist().create();
				if (!"关联疾病".equals(prop) && !"关联症状".equals(prop)){
					nullProperties.add(prop);
				}
			}

 			List<String> sortKeys = new ArrayList<>();
			if (CollUtil.isNotEmpty(linkProperties)){
				for (String s : linkProperties){
					if ("关联疾病".equals(s) || "关联症状".equals(s)){
						sortKeys.add(s);
					}
				}
			}
			if ("疾病_治疗_手术".equals(schemaEdge.getLink())) {
				sortKeys.add("注意事项");
				nullProperties.remove("注意事项");
			}

			if (CollUtil.isEmpty(sortKeys)){
				schema.edgeLabel(schemaEdge.getLink())
						.sourceLabel(schemaEdge.getSourceVertex())
						.targetLabel(schemaEdge.getTargetVertex())
						.properties(linkProperties.toArray(new String[]{}))
						.nullableKeys(nullProperties.toArray(new String[]{}))
						.ifNotExist()
						.create();
			}else {
				schema.edgeLabel(schemaEdge.getLink())
						.sourceLabel(schemaEdge.getSourceVertex())
						.targetLabel(schemaEdge.getTargetVertex())
						.properties(linkProperties.toArray(new String[]{}))
						.nullableKeys(nullProperties.toArray(new String[]{}))
						.frequency(Frequency.MULTIPLE)
						.sortKeys(sortKeys.toArray(new String[]{}))
						.ifNotExist()
						.create();
			}
		}
		log.info("边关系初始化结束");
		return errorList;
	}


	public static void main(String[] args) {
		String filepath = "E:\\西医图谱\\导入标准模板\\图谱模板.xlsx";
		List<SchemaVertex> vertexList = EasyExcel
				.read(filepath)
				.head(SchemaVertex.class)
				.sheet("所有实体")
				.doReadSync();
		vertex(vertexList);

		List<SchemaEdge> edgeList = EasyExcel
				.read(filepath)
				.head(SchemaEdge.class)
				.sheet("所有关系")
				.doReadSync();
		edge(edgeList);
		HGFactory.ins().close();
		System.out.println(vertexList);
	}

}
