package com.aeye.aeaimb.mkpb.controller.cstn;

import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.common.log.annotation.SysLog;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnQuestionDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestion;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestion;
import com.aeye.aeaimb.mkpb.service.cstn.CstnQuestionService;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnQuestionVO;
import com.aeye.aeaimb.mkpb.vo.cstn.QuestionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * [问诊]问题 前端控制器
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@RestController
@RequestMapping("/cstn-question")
public class CstnQuestionController {


	@Autowired
	private CstnQuestionService cstnQuestionService;

	/**
	 * 问题列表分頁查詢
	 * @param dto 输入类
	 * @return 输出对象
	 */
	@PostMapping("/getCstnQuestionPageList")
	public R<IPage<CstnQuestionVO>> getCstnQuestionPageList(@RequestBody CstnQuestionDTO dto){
		Page<CstnQuestion> page = new Page<>(dto.getCurrent(), dto.getSize());
		IPage<CstnQuestionVO> pathPageList = cstnQuestionService.getCstnQuestionPageList(page, dto);
		return R.ok(pathPageList);
	}

	/**
	 * 分页查询
	 * @param page     分页对象
	 * @param cstnQuestion 问诊问题
	 * @return
	 */
	@GetMapping("/page")
	public R getQuestionPage(Page page, CstnQuestion cstnQuestion) {
		return R.ok(cstnQuestionService.page(page, Wrappers.query(cstnQuestion)));
	}

	/**
	 * 查看
	 * @param id id
	 * @return R
	 */
	@GetMapping("/show/{id}")
	public R show(@PathVariable("id") String id) {
		return R.ok(cstnQuestionService.show(id));
	}

	/**
	 * 点击进入编辑页面
	 * @param id id
	 * @return R
	 */
	@GetMapping("/edit/{id}")
	public R edit(@PathVariable("id") String id) {
		return R.ok(cstnQuestionService.edit(id));
	}

	/**
	 * 预览
	 * @param id id
	 * @return R
	 */
	@GetMapping("/preview/{id}")
	public R preview(@PathVariable("id") String id) {
		return R.ok(cstnQuestionService.preview(id));
	}

	/**
	 * 详情
	 * @param cstnQuestion 查询条件
	 * @return R
	 */
	@GetMapping("/query-detail")
	public R getQuestionDetails(CstnQuestion cstnQuestion) {
		return R.ok(cstnQuestionService.getOne(Wrappers.query(cstnQuestion), false));
	}

	/**
	 * 保存
	 *
	 * @param Question 问题
	 * @return R
	 */
	@SysLog("保存问题")
	@PostMapping("/save-question")
	public R saveQuestion(@RequestBody QuestionVO Question) {
		return R.ok(cstnQuestionService.add(Question));
	}

	/**
	 * 编辑问题
	 * @param questionVO 问题
	 * @return R
	 */

	@SysLog("编辑问题")
	@PutMapping("/update-question")
	public R updateQuestion(@Valid @RequestBody QuestionVO questionVO) {
		return R.ok(cstnQuestionService.update(questionVO));
	}

	/**
	 * 删除问题
	 * @param id id
	 * @return R
	 */
	@SysLog("删除问题")
	@DeleteMapping("/del-question/{id}")
	public R removeById(@PathVariable String id) {
		return R.ok(cstnQuestionService.remove(id));
	}

}
