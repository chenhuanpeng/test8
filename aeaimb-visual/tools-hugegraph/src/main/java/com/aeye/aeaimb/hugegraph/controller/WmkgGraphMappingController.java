package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgGraphMappingDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgGraphMappingErrorDto;
import com.aeye.aeaimb.hugegraph.service.WmkgGraphMappingService;
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
 * 图谱标准字典映射(BaseGraphMapping)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 15:58:03
 */
@RestController
@RequestMapping("graphMapping")
public class WmkgGraphMappingController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgGraphMappingService baseGraphMappingService;

	@PostMapping("/batchGraphMapping")
	public boolean batchGraphMapping(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgGraphMappingDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgGraphMappingDto.class)
				.sheet()
				.doReadSync();

		List<WmkgGraphMappingErrorDto> errorDtos = baseGraphMappingService.batchGraphMapping(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\药品" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgGraphMappingErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

