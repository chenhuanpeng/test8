package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictBasicTreatmentDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgServiceDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.CheckTestDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.service.WmkgServiceDictService;
import com.alibaba.excel.EasyExcel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * [西医]服务项目字典(WmkgServiceDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-22 11:54:45
 */
@RestController
@RequestMapping("wmkgServiceDict")
public class WmkgServiceDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgServiceDictService wmkgServiceDictService;

	/**
	 * 批量导入医疗服务字典
	 * @param file 输入文件
	 * @return 错误信息
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchServiceDict")
	public boolean batchServiceDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgServiceDictDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgServiceDictDto.class)
				.sheet()
				.doReadSync();

		List<WmkgServiceDictErrorDto> errorDtos = wmkgServiceDictService.batchServiceDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\医疗服务项目" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgServiceDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 基础项目字典导入
	 * @param file 输入文件
	 * @return 成功失败
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchServiceDictBasic")
	public boolean batchServiceDictBasic(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgServiceDictBasicTreatmentDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgServiceDictBasicTreatmentDto.class)
				.sheet()
				.doReadSync();

		List<WmkgServiceDictErrorDto> errorDtos = wmkgServiceDictService.batchServiceDictBasic(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\医疗服务基础项目" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgServiceDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}



	@PostMapping("/batchServiceDictKgMapping")
	public boolean batchServiceDictKgMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<CheckTestDictKgMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(CheckTestDictKgMappingDto.class)
				.sheet()
				.doReadSync();

		List<WmkgServiceDictErrorDto> errorDtos = wmkgServiceDictService.batchServiceDictKgMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\检查检验的知识库映射项目" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgServiceDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}


	/**
	 * 批量导入指标值字典
	 * @param file 输入文件
	 * @return 成功失败
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchServiceItemDict")
	public boolean batchServiceItemDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgServiceDictItmtDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgServiceDictItmtDto.class)
				.sheet()
				.doReadSync();

		List<WmkgServiceDictErrorDto> errorDtos = wmkgServiceDictService.batchServiceItemDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\医疗服务基础项目字典" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgServiceDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}



}

