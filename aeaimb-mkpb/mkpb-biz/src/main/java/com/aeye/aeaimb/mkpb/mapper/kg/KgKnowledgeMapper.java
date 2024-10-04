package com.aeye.aeaimb.mkpb.mapper.kg;


import com.aeye.cdss.admin.api.dto.GeneralPagingOfKnowledgeBase;
import com.aeye.cdss.admin.api.dto.KnowledgeCount;
import com.aeye.cdss.admin.api.dto.KnowledgeDiseaseDTO;
import com.aeye.cdss.admin.api.entity.KgKnowledge;
import com.aeye.cdss.admin.api.vo.KgKnowledgeFieldVO;
import com.aeye.cdss.admin.api.vo.KnowledgeListInParameter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KgKnowledgeMapper extends BaseMapper<KgKnowledge> {
	public List<KnowledgeCount> homePage();

	 IPage<KnowledgeDiseaseDTO> getDiseaseList(@Param("page") Page page, @Param("kgKnowledge") KnowledgeListInParameter kgKnowledge);


	List<KnowledgeDiseaseDTO> getUniversalPaging(@Param("type") String type,
												  @Param("name") String name,
												  @Param("lookup") List<GeneralPagingOfKnowledgeBase> lookup,
												  @Param("where") List<GeneralPagingOfKnowledgeBase> where,@Param("current") Long current,@Param("size") Long size);

	Long countUniversalPaging(@Param("type") String type, @Param("name") String name);


	List<KgKnowledgeFieldVO> getsTheListOfNewItems(@Param("kgid") String kgid, @Param("type") String type) ;

	List<KgKnowledgeFieldVO> getPreviewData(@Param("kgid") String kgid, @Param("type") String type) ;
}