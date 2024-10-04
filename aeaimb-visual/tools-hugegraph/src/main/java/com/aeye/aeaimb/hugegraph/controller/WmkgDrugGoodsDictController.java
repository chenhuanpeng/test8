package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugDictErrorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.WmkgDrugGoodsDictDto;
import com.aeye.aeaimb.hugegraph.service.WmkgDrugGoodsDictService;
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
 * [西医]药品商品字典(WmkgDrugGoodsDict)表控制层
 *
 * @author linkeke
 * @since 2024-08-30 09:08:04
 */
@RestController
@RequestMapping("wmkgDrugGoodsDict")
public class WmkgDrugGoodsDictController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgDrugGoodsDictService wmkgDrugGoodsDictService;

	/**
	 * 批量导入药品商品字典
	 * @param file 输入文件
	 * @return boolean
	 * @throws IOException 异常
	 */
	@PostMapping("/batchDrugGoodsDict")
	public boolean batchDrugGoodsDict(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<WmkgDrugGoodsDictDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(WmkgDrugGoodsDictDto.class)
				.sheet()
				.doReadSync();

		List<WmkgDrugDictErrorDto> errorDtos = wmkgDrugGoodsDictService.batchDrugGoodsDict(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\药品商品字典" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", WmkgDrugDictErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

