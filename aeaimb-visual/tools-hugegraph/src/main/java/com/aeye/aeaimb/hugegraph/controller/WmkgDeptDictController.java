package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.*;
import com.aeye.aeaimb.hugegraph.service.WmkgDeptDictService;
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
 * 科室字典(BaseDeptDict)表控制层
 *
 * @author linkeke
 * @since 2024-07-21 11:32:44
 */
@RestController
@RequestMapping("baseDeptDict")
public class WmkgDeptDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDeptDictService baseDeptDictService;

	/**
	 * 科室字典导入
	 * @param file 输入文件
	 * @return 异常反馈
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchDeptDict")
	public boolean batchDeptDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgDeptDictDto> dictDtos = EasyExcel
				.read(file.getInputStream())
				.head(WmkgDeptDictDto.class)
				.sheet()
				.doReadSync();
		List<WmkgDeptDictErrorDto> errorDtos = baseDeptDictService.batchDeptDict(dictDtos);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\科室字典" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDeptDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

