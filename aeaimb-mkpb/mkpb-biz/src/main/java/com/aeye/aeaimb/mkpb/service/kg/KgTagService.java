package com.aeye.aeaimb.mkpb.service.kg;

import com.aeye.cdss.admin.api.dto.TagNode;
import com.aeye.cdss.admin.api.entity.KgTag;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

@DS("cdss_knowledge")
public interface KgTagService extends IService<KgTag> {


	List<TagNode> getTreeStructure(String type) ;

	KgTag getTagById(String id) ;


	List<String> findAllParentIds(String initialParentId) ;


}