package com.aeye.aeaimb.hugegraph.service;

import com.aeye.aeaimb.hugegraph.controller.dto.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * [西医]科室(WmkgDept)表服务接口
 *
 * @author linkeke
 * @since 2024-06-24 11:19:05
 */
public interface WmkgHugeGraphService {

	void initSchemeVertex(List<SchemaDto> schemaDtoList);

	List<SchemaEdgeErrorDto> initSchemeEdge(List<SchemaEdgeDto> schemaDtoList);

	List<FileErrorDto> unmergeCell(File unzip) throws IOException;
	List<SaveDataErrorDto> batchSaveData(List<DataDto> schemaDtoList);
	List<SaveDataErrorDto> batchSaveSpecial(List<DataDto> schemaDtoList);

	List<DataDto> findListByFuzzyKey(Map<String, List<DataDto>> map, String fuzzyKey);

	List<SchemaEdgeErrorDto> batchSaveDrugCategory(List<DrugCategoryDto> drugCategoryList);

	List<HealthDrugErrorDto> healthInsuranceDrug(List<HealthDrugDto> schemaDtoList);

	List<ImportDataDto> getImportData(List<DataDto> schemaDtoList);
}
