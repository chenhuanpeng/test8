package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.KgKnowledge;
import com.aeye.aeaimb.hugegraph.mapper.KgKnowledgeMapper;
import com.aeye.aeaimb.hugegraph.service.KgKnowledgeService;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * (KgKnowledge)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-14 09:43:36
 */
@Service("kgKnowledgeService")
@DS("cdss_knowledge")
public class KgKnowledgeServiceImpl extends ServiceImpl<KgKnowledgeMapper, KgKnowledge> implements KgKnowledgeService {
	@Resource
	private KgKnowledgeMapper kgKnowledgeMapper;


}
