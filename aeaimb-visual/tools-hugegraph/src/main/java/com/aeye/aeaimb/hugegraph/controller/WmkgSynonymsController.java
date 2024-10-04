package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSynonymsDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgSynonymsErrorDto;
import com.aeye.aeaimb.hugegraph.service.WmkgSynonymsService;
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
 * [西医]同义词(WmkgSynonyms)表控制层
 *
 * @author linkeke
 * @since 2024-07-17 13:33:23
 */
@RestController
@RequestMapping("wmkgSynonyms")
public class WmkgSynonymsController {
    /**
     * 服务对象
     */
    @Resource
    private WmkgSynonymsService wmkgSynonymsService;

	/**
	 * 同义词导入
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/initSynonyms")
	public boolean initSynonyms(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgSynonymsDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgSynonymsDto.class)
				.sheet()
				.doReadSync();

		List<WmkgSynonymsErrorDto> errorDtos = wmkgSynonymsService.initSynonyms(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\同义词" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgSynonymsErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

