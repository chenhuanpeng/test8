package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSymptomDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSymptomDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.SymptomDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.service.WmkgSymptomDictService;
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
 * 症状字典(BaseSymptomDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 15:39:49
 */
@RestController
@RequestMapping("baseSymptomDict")
public class WmkgSymptomDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgSymptomDictService baseSymptomDictService;

	@PostMapping("/batchSymptomDict")
	public boolean batchSymptomDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgSymptomDictDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgSymptomDictDto.class)
				.sheet()
				.doReadSync();

		List<WmkgSymptomDictErrorDto> errorDtos = baseSymptomDictService.batchSymptomDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\症状" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgSymptomDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}


	@PostMapping("/batchSymptomDictKgMapping")
	public boolean batchSymptomDictKgMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<SymptomDictKgMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(SymptomDictKgMappingDto.class)
				.sheet()
				.doReadSync();

		List<WmkgSymptomDictErrorDto> errorDtos = baseSymptomDictService.batchSymptomDictKgMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\症状映射知识库" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgSymptomDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}


}

