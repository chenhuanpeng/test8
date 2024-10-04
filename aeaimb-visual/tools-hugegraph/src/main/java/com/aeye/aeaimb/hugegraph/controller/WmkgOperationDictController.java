package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgOperateDictDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgOperateDictErrorDto;
import com.aeye.aeaimb.hugegraph.service.WmkgOperationDictService;
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
 * 手术字典(BaseOperationDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 15:15:36
 */
@RestController
@RequestMapping("baseOperationDict")
public class WmkgOperationDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgOperationDictService baseOperationDictService;

	/**
	 * 批量导入手术字典
	 * @param file 输入文件
	 * @return 错误反馈
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchOperationDict")
	public boolean batchOperationDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgOperateDictDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgOperateDictDto.class)
				.sheet()
				.doReadSync();

		List<WmkgOperateDictErrorDto> errorDtos = baseOperationDictService.batchOperationDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\手术" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgOperateDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

