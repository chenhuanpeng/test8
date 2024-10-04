package com.aeye.aeaimb.mkpb.service.cstn;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPurposeDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnCondition;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPurpose;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPurposeVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathAuditVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PurposeShowVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chenhuanpeng
 * @since 2024-08-30 15:23:08
 */
public interface CstnPurposeService extends IService<CstnPurpose> {

	IPage<CstnPurposeVO> getCstnPurposePageList(Page<CstnPurpose> page, CstnPurposeDTO dto);

	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	boolean add(PurposeShowVO entity);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	boolean remove(String id);

	/**
	 * 编辑
	 * @param entity
	 * @return
	 */
	boolean update(PurposeShowVO entity);

	/**
	 * 查看
	 * @param id
	 * @return
	 */
	PurposeShowVO show(String id);

	/**
	 * 点击编辑进入页面
	 * @param id
	 * @return
	 */
	PurposeShowVO edit(String id);

	/**
	 * 预览
	 * @param id
	 * @return
	 */
	PurposeShowVO preview(String id);

	List<Tree<String>> selectTree();

	List<Tree<String>> getParentPurposeList();


}
