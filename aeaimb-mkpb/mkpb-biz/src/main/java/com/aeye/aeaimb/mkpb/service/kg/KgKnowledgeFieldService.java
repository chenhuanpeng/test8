package com.aeye.aeaimb.mkpb.service.kg;

import com.aeye.cdss.admin.api.vo.KgKnowledgeFieldVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aeye.cdss.admin.api.entity.KgKnowledgeField;

import java.util.List;

@DS("cdss_knowledge")
public interface KgKnowledgeFieldService extends IService<KgKnowledgeField> {

	List<KgKnowledgeFieldVO> getAListOfItems(String type,boolean lookId);

}