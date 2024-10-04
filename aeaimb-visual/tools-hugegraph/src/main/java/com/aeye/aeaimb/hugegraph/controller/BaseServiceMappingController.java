package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseServiceMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseServiceMappingErrorDto;
import com.aeye.aeaimb.hugegraph.service.BaseServiceMappingService;
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
 * 医院服务项目映射(BaseServiceMapping)表控制层
 *
 * @author linkeke
 * @since 2024-07-22 17:16:04
 */
@RestController
@RequestMapping("baseServiceMapping")
public class BaseServiceMappingController {
	/**
	 * 服务对象
	 */
	@Resource
	private BaseServiceMappingService baseServiceMappingService;

	@PostMapping("/batchSaveServiceMapping")
	public boolean batchSaveServiceMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<BaseServiceMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(BaseServiceMappingDto.class)
				.sheet()
				.doReadSync();

		List<BaseServiceMappingErrorDto> errorDtos = baseServiceMappingService.batchSaveServiceMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\医疗服务项目映射数据" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", BaseServiceMappingErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

