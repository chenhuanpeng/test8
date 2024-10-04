package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDiseaseDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DiseaseDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.kg.DrugDictKgMappingDto;
import com.aeye.aeaimb.hugegraph.service.WmkgDrugDictService;
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
 * 药品字典(BaseDrugDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 15:58:02
 */
@RestController
@RequestMapping("baseDrugDict")
public class WmkgDrugDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDrugDictService baseDrugDictService;

	@PostMapping("/batchDrugDict")
	public boolean batchDrugDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgDrugDictDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgDrugDictDto.class)
				.sheet()
				.doReadSync();

		List<WmkgDrugDictErrorDto> errorDtos = baseDrugDictService.batchDrugDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\药品" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDrugDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

	@PostMapping("/batchDrugDictKgMapping")
	public boolean batchDrugDictKgMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DrugDictKgMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DrugDictKgMappingDto.class)
				.sheet()
				.doReadSync();

		List<WmkgDrugDictErrorDto> errorDtos = baseDrugDictService.batchDrugDictKgMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\药品知识库映射" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDiseaseDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

