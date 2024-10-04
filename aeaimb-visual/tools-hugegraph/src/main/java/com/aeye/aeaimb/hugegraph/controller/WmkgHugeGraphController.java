package com.aeye.aeaimb.hugegraph.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ZipUtil;
import com.aeye.aeaimb.hugegraph.controller.dto.*;
import com.aeye.aeaimb.hugegraph.service.WmkgHugeGraphService;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * [西医]科室(WmkgDept)表控制层
 *
 * @author linkeke
 * @since 2024-06-24 11:19:04
 */
@RestController
@RequestMapping("wmkgHugeGraph")
public class WmkgHugeGraphController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgHugeGraphService wmkgHugeGraphService;

	/**
	 * 初始化实体
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/initSchemeVertex")
	public boolean initSchemeVertex(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<SchemaDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(SchemaDto.class)
				.sheet()
				.doReadSync();

		wmkgHugeGraphService.initSchemeVertex(dtoList);
		return true;
	}

	/**
	 * 初始化边关系
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/initSchemeEdge")
	public boolean initSchemeEdge(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<SchemaEdgeDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(SchemaEdgeDto.class)
				.sheet()
				.doReadSync();

		List<SchemaEdgeErrorDto> errorDtos = wmkgHugeGraphService.initSchemeEdge(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\关系" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SchemaEdgeErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 取消合并单元格
	 * @param file 输入文件
	 * @return 是否成功
	 * @throws IOException io异常
	 */
	@PostMapping("/unmergeCell")
	public boolean unmergeCell(@RequestParam(value = "file") MultipartFile file) throws IOException {
		File unzip = ZipUtil
				.unzip(file.getInputStream(), FileUtil.file("/tmp/" + System.currentTimeMillis() + "/"), CharsetUtil.CHARSET_GBK);
		List<FileErrorDto> errorDtos = wmkgHugeGraphService.unmergeCell(unzip);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\取消合并单元格" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", FileErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 批量导入疾病图谱数据
	 *
	 * @param file 输入文件
	 * @return 是否成功
	 * @throws IOException 异常
	 */
	@PostMapping("/batchSaveData")
	public boolean batchSaveData(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DataDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DataDto.class)
				.sheet()
				.doReadSync();

		String name = file.getOriginalFilename();

		List<SaveDataErrorDto> errorDtos = wmkgHugeGraphService.batchSaveData(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\" + name + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SaveDataErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 批量导入药品分类数据
	 *
	 * @param file 输入文件
	 * @return 是否成功
	 * @throws IOException 异常
	 */
	@PostMapping("/batchSaveDrugCategory")
	public boolean batchSaveDrugCategory(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DrugCategoryDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DrugCategoryDto.class)
				.sheet()
				.doReadSync();

		List<SchemaEdgeErrorDto> errorDtos = wmkgHugeGraphService.batchSaveDrugCategory(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\关系" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SchemaEdgeErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * omama医保用药规则
	 * @param file 输入文件
	 * @return 成功失败
	 * @throws IOException io异常
	 */
	@PostMapping("/healthInsuranceDrug")
	public boolean healthInsuranceDrug(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<HealthDrugDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(HealthDrugDto.class)
				.sheet()
				.doReadSync();

		List<HealthDrugErrorDto> errorDtos = wmkgHugeGraphService.healthInsuranceDrug(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\医保用药规则" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", HealthDrugErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}


	@PostMapping("/getImportData")
	public boolean getImportData(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DataDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DataDto.class)
				.sheet()
				.doReadSync();

		List<ImportDataDto> errorDtos = wmkgHugeGraphService.getImportData(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\抽取数据\\训练数据" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", ImportDataDto.class)
				.sheet("训练数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 批量导入疾病特殊建议
	 * @param file 输入文件
	 * @return 错误数据
	 * @throws IOException io异常
	 */
	@PostMapping("/batchSaveSpecial")
	public boolean batchSaveSpecial(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DataDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DataDto.class)
				.sheet()
				.doReadSync();

		String name = file.getOriginalFilename();

		List<SaveDataErrorDto> errorDtos = wmkgHugeGraphService.batchSaveSpecial(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\" + name + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SaveDataErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

