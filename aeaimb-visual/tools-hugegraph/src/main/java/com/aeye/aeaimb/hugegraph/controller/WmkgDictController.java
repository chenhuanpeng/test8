package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.*;
import com.aeye.aeaimb.hugegraph.service.WmkgDictService;
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
 * 字典表(SysDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-19 18:42:19
 */
@RestController
@RequestMapping("sysDict")
public class WmkgDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDictService wmkgDictService;


	/**
	 * 基础字典数据导入
	 * @param file excel文件
	 * @return 反馈文档
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchSysDict")
	public boolean batchSysDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<SysDictDto> dictDtos = EasyExcel
				.read(file.getInputStream())
				.head(SysDictDto.class)
				.sheet("基础字典类型")
				.doReadSync();

		List<SysDictItemDto> sysDictItemDtos = EasyExcel
				.read(file.getInputStream())
				.head(SysDictItemDto.class)
				.sheet("基础字典明细")
				.doReadSync();

		List<SysDictErrorDto> errorDtos = wmkgDictService.batchSysDict(dictDtos, sysDictItemDtos);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\基础字典" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SysDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

