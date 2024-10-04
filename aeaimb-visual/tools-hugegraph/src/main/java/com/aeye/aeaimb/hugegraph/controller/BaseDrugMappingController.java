package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDrugMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDrugMappingErrorDto;
import com.aeye.aeaimb.hugegraph.service.BaseDrugMappingService;
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
 * 医院药品映射(BaseDrugMapping)表控制层
 *
 * @author linkeke
 * @since 2024-07-22 18:07:37
 */
@RestController
@RequestMapping("baseDrugMapping")
public class BaseDrugMappingController {
	/**
	 * 服务对象
	 */
	@Resource
	private BaseDrugMappingService baseDrugMappingService;

	@PostMapping("/batchSaveDrugMapping")
	public boolean batchSaveDrugMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<BaseDrugMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(BaseDrugMappingDto.class)
				.sheet()
				.doReadSync();

		List<BaseDrugMappingErrorDto> errorDtos = baseDrugMappingService.batchSaveDrugMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\药品映射数据" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", BaseDrugMappingErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

