package com.aeye.aeaimb.mkpb.service.cstn;

import cn.hutool.core.lang.tree.Tree;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnMeta;
import com.aeye.aeaimb.mkpb.vo.cstn.CommonVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chenhuanpeng
 * @since 2024-08-30 15:23:08
 */
public interface CstnMetaService extends IService<CstnMeta> {

	List<Tree<String>> queryMetaDataList(String bitFlag);


	List<CommonVO>  queryCommonList(String keyword, String id);

}
