package com.aeye.aeaimb.mkpb.service.cstn;

import com.aeye.aeaimb.mkpb.dto.cstn.CstnQuestionDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnCondition;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPurpose;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestion;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnQuestionVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathAuditVO;
import com.aeye.aeaimb.mkpb.vo.cstn.QuestionVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author chenhuanpeng
 * @since 2024-08-30 15:23:08
 */
public interface CstnQuestionService extends IService<CstnQuestion> {

	IPage<CstnQuestionVO> getCstnQuestionPageList(Page<CstnQuestion> page, CstnQuestionDTO dto);

	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	boolean add(QuestionVO entity);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	boolean remove(String id);

	/**
	 * 编辑
	 * @param questionVO
	 * @return
	 */
	boolean update(QuestionVO questionVO);

	/**
	 * 查看
	 * @param id
	 * @return
	 */
	QuestionVO show(String id);

	/**
	 * 点击编辑进入页面
	 * @param id
	 * @return
	 */
	QuestionVO edit(String id);

	/**
	 * 预览
	 * @param id
	 * @return
	 */
	QuestionVO preview(String id);

}
