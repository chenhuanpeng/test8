package com.aeye.aeaimb.hugegraph.mapper;

import com.aeye.aeaimb.hugegraph.mapper.entity.KgKnowledge;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * (KgKnowledge)表数据库访问层
 *
 * @author linkeke
 * @since 2024-08-14 09:43:36
 */
@Mapper
@DS("cdss_knowledge")
public interface KgKnowledgeMapper extends BaseMapper<KgKnowledge> {


}

