package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.CriticalValueDto;
import com.aeye.aeaimb.hugegraph.controller.dto.CriticalValueErrorDto;
import com.aeye.aeaimb.hugegraph.service.WmkgCrisisService;
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
 * [西医]危机值(WmkgCrisis)表控制层
 *
 * @author linkeke
 * @since 2024-07-19 17:21:49
 */
@RestController
@RequestMapping("wmkgCrisis")
public class WmkgCrisisController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgCrisisService wmkgCrisisService;

	/**
	 * 危急值数据保存
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/batchSaveCriticalValue")
	public boolean batchSaveCriticalValue(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<CriticalValueDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(CriticalValueDto.class)
				.sheet()
				.doReadSync();

		List<CriticalValueErrorDto> errorDtos = wmkgCrisisService.batchSaveCriticalValue(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\危急值" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", CriticalValueErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

