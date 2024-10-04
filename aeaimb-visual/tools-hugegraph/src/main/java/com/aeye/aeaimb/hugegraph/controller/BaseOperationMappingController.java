package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseOperationMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseOperationMappingErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.OperationDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.service.BaseOperationMappingService;
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
 * 医院手术映射(BaseOperationMapping)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@RestController
@RequestMapping("baseOperationMapping")
public class BaseOperationMappingController {
	/**
	 * 服务对象
	 */
	@Resource
	private BaseOperationMappingService baseOperationMappingService;

	@PostMapping("/batchSaveOperationMapping")
	public boolean batchSaveOperationMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<BaseOperationMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(BaseOperationMappingDto.class)
				.sheet()
				.doReadSync();

		List<BaseOperationMappingErrorDto> errorDtos = baseOperationMappingService.batchSaveOperationMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\手术映射数据" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", BaseOperationMappingErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}


	@PostMapping("/batchSaveOperationKgMapping")
	public boolean batchSaveOperationKgMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<OperationDictKgMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(OperationDictKgMappingDto.class)
				.sheet()
				.doReadSync();

		List<BaseOperationMappingErrorDto> errorDtos = baseOperationMappingService.batchSaveOperationKgMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\手术知识库映射数据" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", BaseOperationMappingErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

