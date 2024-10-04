package com.aeye.aeaimb.mkpb.service.impl.cstn;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aeye.aeaimb.common.core.constant.CommonConstants;
import com.aeye.aeaimb.common.core.constant.MetaDictConstants;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.dto.cstn.MetaDataDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnDict;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnMeta;
import com.aeye.aeaimb.mkpb.mapper.cstn.CstnDictMapper;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDiseaseDictMapper;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgSymptomDictMapper;
import com.aeye.aeaimb.mkpb.service.cstn.CstnDictService;
import com.aeye.aeaimb.mkpb.vo.cstn.CommonVO;
import com.aeye.aeaimb.mkpb.vo.cstn.MetaDataVO;
import com.aeye.cdss.admin.api.entity.BaseDeptDict;
import com.aeye.cdss.admin.api.entity.BaseDeptDoctor;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aeye.aeaimb.mkpb.mapper.cstn.CstnMetaMapper;
import com.aeye.aeaimb.mkpb.service.cstn.CstnMetaService;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * [问诊]问诊条件元数据 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Service
public class CstnMetaServiceImpl extends ServiceImpl<CstnMetaMapper, CstnMeta> implements CstnMetaService {

	@Resource
	private CstnMetaMapper cstnMetaMapper;

	@Resource
	private CstnDictMapper dictMapper;

	@Resource
	private WmkgSymptomDictMapper symptomDictMapper;

	@Resource
	private WmkgDiseaseDictMapper diseaseDictMapper;

	@Override
	public List<Tree<String>> queryMetaDataList(String bitFlag) {

		List<MetaDataDTO> metaDataList = cstnMetaMapper.queryMetaDataList(bitFlag);
		if (CollUtil.isEmpty(metaDataList)) {
			return Collections.emptyList();
		}


		List<TreeNode<String>> collect = metaDataList.stream()
				.filter(meta -> !meta.getId().equals(meta.getParentMeta()))
				.map(meta -> {
					TreeNode<String> treeNode = new TreeNode();
					treeNode.setId(meta.getId());

					if (StringUtils.isBlank(meta.getParentMeta())) {
						meta.setParentMeta(CommonConstants.ZERO);
					}
					treeNode.setParentId(meta.getParentMeta());
					treeNode.setName(meta.getMetaName());
					treeNode.setWeight(meta.getMetaLvl());

					Map<String, Object> extra = new HashMap<>(8);
					extra.put("dataType", meta.getMetaDataType());
					extra.put("queryType", meta.getMetaDict());
					extra.put("unit", meta.getUnit());
					String metaDict = meta.getMetaDict();
					if(StringUtil.isNotEmpty(metaDict)){
						List<CstnDict> list = dictMapper.getDictList(metaDict);
						if (CollUtil.isNotEmpty(list)) {
							extra.put("dictData", list);
						}
					}

					treeNode.setExtra(extra);

					return treeNode;
				})
				.collect(Collectors.toList());

		return TreeUtil.build(collect, CommonConstants.ZERO);
	}

	public List<CommonVO>  queryCommonList(String keyword,String id){

		if(StringUtil.isEmpty(keyword)){
			throw BusinessException.create(SystemMessage.KEYWORD_FRONT_MUST);
		}

		if(StringUtil.isEmpty(id)){
			throw BusinessException.create(SystemMessage.ID_FRONT_MUST);
		}

		CstnMeta meta = this.getById(id);
		String sql = meta.getMetaSql();
		sql = sql.replace("#{likename}", "'%"+keyword+"%'");

		List<CommonVO> commonVOS = cstnMetaMapper.querySql(sql);

		return commonVOS;
	}


}
