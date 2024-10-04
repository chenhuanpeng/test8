package com.aeye.aeaimb.hugegraph.controller;

import com.aeye.aeaimb.hugegraph.controller.dto.DataDto;
import com.aeye.aeaimb.hugegraph.controller.dto.ReasonFactorDto;
import com.aeye.aeaimb.hugegraph.controller.dto.SaveDataErrorDto;
import com.aeye.aeaimb.hugegraph.service.WmkgReasonFactorService;
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
 * 推理因子分类(WmkgReasonFactor)表控制层
 *
 * @author linkeke
 * @since 2024-08-03 13:40:28
 */
@RestController
@RequestMapping("wmkgReasonFactor")
public class WmkgReasonFactorController {
	/**
	 * 服务对象
	 */
	@Resource
	private WmkgReasonFactorService wmkgReasonFactorService;

	/**
	 * 规则分类数据导入
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/batchReasonFactor")
	public boolean batchReasonFactor(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<ReasonFactorDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(ReasonFactorDto.class)
				.sheet()
				.doReadSync();

		List<SaveDataErrorDto> errorDtos = wmkgReasonFactorService.batchReasonFactor(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\规则分类" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SaveDataErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}


	/**
	 * 保存推荐疾症状检查检验等
	 * @param file 输入文件
	 * @return 保存结果
	 * @throws IOException IO异常
	 */
	@PostMapping("/batchDiagReasonObjects")
	public boolean batchDiagReasonObjects(@RequestParam(value = "file") MultipartFile file) throws IOException {
		List<DataDto> dtoList = EasyExcel
				.read(file.getInputStream())
				.head(DataDto.class)
				.sheet()
				.doReadSync();

		List<SaveDataErrorDto> errorDtos = wmkgReasonFactorService.batchDiagReasonObjects(dtoList);
		EasyExcel.write("E:\\西医图谱\\导入标准模板\\导入失败数据\\规则库中疾病症状检查检验" + new SimpleDateFormat("yyyyMMddMMHHss").format(new Date()) + ".xlsx", SaveDataErrorDto.class)
				.sheet("导入反馈数据") // 创建sheet
				.doWrite(errorDtos); // 写入数据
		return true;
	}

}

