package com.aeye.aeaimb.mkpb.service.kg;

import com.aeye.aeaimb.mkpb.entity.sys.SysDictItem;
import com.aeye.cdss.admin.api.dto.GeneralPagingOfKnowledgeBase;
import com.aeye.cdss.admin.api.dto.KnowledgeCount;
import com.aeye.cdss.admin.api.dto.KnowledgeDiseaseDTO;
import com.aeye.cdss.admin.api.dto.KnowledgePreviewDTO;
import com.aeye.cdss.admin.api.vo.KgKnowledgeFieldVO;
import com.aeye.cdss.admin.api.vo.KnowledgeListInParameter;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.aeye.cdss.admin.api.entity.KgKnowledge;

import java.util.List;
import java.util.Map;

@DS("cdss_knowledge")
public interface KgKnowledgeService extends IService<KgKnowledge> {

	public List<KnowledgeCount> knowledgePaging();

	/**
	 * 疾病分页
	 * @param page
	 * @param kgKnowledge
	 * @return
	 */
	public IPage<KnowledgeDiseaseDTO> getDiseaseList(Page page, KnowledgeListInParameter kgKnowledge);


	/**
	 * 通用分页
	 * @param page
	 * @param name
	 * @param lookup
	 * @param where
	 * @return
	 */
	public IPage<KnowledgeDiseaseDTO> getUniversalPaging(Page page,String type, String name, List<GeneralPagingOfKnowledgeBase> lookup,List<GeneralPagingOfKnowledgeBase> where);

	public Map<String,List<GeneralPagingOfKnowledgeBase>> setKeys(KnowledgeListInParameter kgKnowledge);

	public List<KgKnowledgeFieldVO> getsTheListOfNewItems(String kgid);

	public KnowledgePreviewDTO getPreviewData(String kgid);


	public Boolean modifyAndAdd(String kgid, List<KgKnowledgeFieldVO> list, Map<String,Map<String, SysDictItem>> dicMap);

	public Boolean deleteKnowledge(String kgid);

}