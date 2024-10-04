package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictLabelDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DiseaseDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseDictService;
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
 * 疾病字典(BaseDiseaseDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 12:08:39
 */
@RestController
@RequestMapping("baseDiseaseDict")
public class WmkgDiseaseDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDiseaseDictService baseDiseaseDictService;

	/**
	 * 疾病数据保存
	 * @param file 导入数据
	 * @return 错误数据
	 * @throws IOException 异常
	 */
	@PostMapping("/batchDiseaseDict")
	public boolean batchDiseaseDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgDiseaseDictDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgDiseaseDictDto.class)
				.sheet()
				.doReadSync();

		List<WmkgDiseaseDictErrorDto> errorDtos = baseDiseaseDictService.batchDiseaseDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\疾病" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDiseaseDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 疾病知识库映射
	 * @param file 导入数据
	 * @return 成功或失败
	 * @throws IOException 异常
	 */
	@PostMapping("/batchDiseaseDictKgMapping")
	public boolean batchDiseaseDictKgMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DiseaseDictKgMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DiseaseDictKgMappingDto.class)
				.sheet()
				.doReadSync();

		List<WmkgDiseaseDictErrorDto> errorDtos = baseDiseaseDictService.batchDiseaseDictKgMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\疾病知识库映射" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDiseaseDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	/**
	 * 疾病标签导入
	 * @param file 导入数据
	 * @return  成功或失败
	 * @throws IOException 异常
	 */
	@PostMapping("/batchDiseaseDictLabel")
	public boolean batchDiseaseDictLabel(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgDiseaseDictLabelDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgDiseaseDictLabelDto.class)
				.sheet()
				.doReadSync();

		List<WmkgDiseaseDictErrorDto> errorDtos = baseDiseaseDictService.batchDiseaseDictLabel(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\疾病标签" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDiseaseDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

