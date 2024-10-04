package com.aeye.aeaimb.mkpb.service.cstn;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.dto.sys.SysLogDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnCondition;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPath;
import com.aeye.aeaimb.mkpb.vo.cstn.CstnPathVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathAuditVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathRelatePurposeVO;
import com.aeye.aeaimb.mkpb.vo.cstn.PathVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author chenhuanpeng
 * @since 2024-08-30 15:23:08
 */
public interface CstnPathService extends IService<CstnPath> {

	IPage<CstnPathVO> getCstnPathPageList(Page<CstnPath> page, CstnPathDTO dto);

	/**
	 * 新增
	 * @param entity
	 * @return
	 */
	boolean add(PathVO entity);

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
	boolean update(PathVO entity);

	/**
	 * 查看
	 * @param id
	 * @return
	 */
	PathVO show(String id);

	/**
	 * 点击编辑进入页面
	 * @param id
	 * @return
	 */
	PathVO edit(String id);

	/**
	 * 预览
	 * @param id
	 * @return
	 */
	PathVO preview(String id);

	boolean submit(String id);

	boolean returnEdit(String id);

	/**
	 * 关联目标
	 * @param relatePurposeVO
	 * @return
	 */
	boolean relatePurpose( PathRelatePurposeVO relatePurposeVO);


	/**
	 * 复制
	 * @param id
	 * @return
	 */
	boolean copy(String id);

	boolean online(String id);

	boolean offline(String id);

	boolean batchOffline(String[] ids);

	boolean batchOnline(String[] ids);

	boolean batchAudit(PathAuditVO auditVO);


	boolean audit(PathAuditVO auditVO);


	List<Tree<String>> selectTree();

	List<Tree<String>> queryAllDepartment();


	List<Tree<String>> getRelatePurposeList(String pathId);


}
