package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.BaseDiseaseMappingErrorDto;
import com.aeye.aeaimb.hugegraph.service.BaseDiseaseMappingService;
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
 * 医院疾病映射(BaseDiseaseMapping)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@RestController
@RequestMapping("baseDiseaseMapping")
public class BaseDiseaseMappingController {
	/**
	 * 服务对象
	 */
	@Resource
	private BaseDiseaseMappingService baseDiseaseMappingService;

	/**
	 *  疾病与院方数据映射
	 * @param file 导入文件
	 * @return 成功失败
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchSaveDiseaseMapping")
	public boolean batchSaveDiseaseMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<BaseDiseaseMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(BaseDiseaseMappingDto.class)
				.sheet()
				.doReadSync();

		List<BaseDiseaseMappingErrorDto> errorDtos = baseDiseaseMappingService.batchSaveDiseaseMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\疾病映射数据" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", BaseDiseaseMappingErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

