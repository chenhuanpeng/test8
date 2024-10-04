package com.aeye.aeaimb.mkpb.service.impl.cstn;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aeye.aeaimb.common.core.constant.CommonConstants;
import com.aeye.aeaimb.common.core.constant.enums.ConditionTypeEnum;
import com.aeye.aeaimb.common.core.constant.enums.OpEnum;
import com.aeye.aeaimb.common.core.constant.enums.OperationEnum;
import com.aeye.aeaimb.common.core.constant.enums.RefTypeEnum;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPurposeDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.*;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPurpose;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnPurpose;
import com.aeye.aeaimb.mkpb.mapper.cstn.*;
import com.aeye.aeaimb.mkpb.mapper.cstn.CstnPurposeMapper;
import com.aeye.aeaimb.mkpb.service.cstn.CstnConditionService;
import com.aeye.aeaimb.mkpb.service.cstn.CstnOperationHistoryService;
import com.aeye.aeaimb.mkpb.service.cstn.CstnPurposeService;
import com.aeye.aeaimb.mkpb.vo.cstn.*;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * [问诊]目标 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Service
@DS("wmkg")
public class CstnPurposeServiceImpl extends ServiceImpl<CstnPurposeMapper, CstnPurpose> implements CstnPurposeService {

	public static final String PURPOSE = "purpose";

	@Resource
	private CstnOperationHistoryService operationHistoryService;

	@Resource
	private CstnPurposeMapper mapper;

	@Resource
	private CstnMetaMapper metaMapper;

	@Resource
	private CstnConditionMapper conditionMapper;

	@Resource
	private CstnConditionService cstnConditionService;

	@Resource
	private CstnPathPurposeMapper cstnPathPurposeMapper;

	@Resource
	private CstnQuestionPurposeMapper cstnQuestionPurposeMapper;


	@Override
	public IPage<CstnPurposeVO> getCstnPurposePageList(Page<CstnPurpose> page, CstnPurposeDTO dto) {

		IPage<CstnPurposeVO> purposeVOIPage = mapper.getCstnPurposePageList(page, dto);
		List<CstnPurposeVO> purposeVOS = purposeVOIPage.getRecords();
		for(CstnPurposeVO vo:purposeVOS){
			String parentId = vo.getParentPurpose();
			if(StringUtil.isNotEmpty(parentId)){
				CstnPurpose purpose = this.getById(parentId);
				if(Objects.nonNull(purpose)){
					vo.setParentPurposeName(purpose.getPurposeName());
				}
			}

			List<ConditionVO> conditions = conditionMapper.getConditionList(vo.getId(), RefTypeEnum.PURPOSE_CONDITION.getMessage(),
					ConditionTypeEnum.SEL_CONDITION.getMessage());
			if(CollUtil.isNotEmpty(conditions)){
				StringBuilder sb = new StringBuilder();
				for(ConditionVO conditionVO:conditions){
					sb.append(conditionVO.getConditionTypeName()+
							(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())
							+conditionVO.getTargetValue()+
							(StringUtil.isNotEmpty(conditionVO.getUnit())?conditionVO.getUnit():Strings.EMPTY)+";");
				}
				if(StringUtil.isNotEmpty(sb.toString())){
					vo.setSelCondition(sb.substring(0,sb.toString().length()-1));
				}
			}

			List<ConditionVO> conditionList = conditionMapper.getConditionList(vo.getId(), RefTypeEnum.PURPOSE_CONDITION.getMessage(),
					ConditionTypeEnum.SKIP_CONDITION.getMessage());
			if(CollUtil.isNotEmpty(conditionList)){
				StringBuilder sb = new StringBuilder();
				for(ConditionVO conditionVO:conditionList){
					sb.append(conditionVO.getConditionTypeName()+(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?
							Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())+conditionVO.getTargetValue()+
							(StringUtil.isNotEmpty(conditionVO.getUnit())?conditionVO.getUnit():Strings.EMPTY)+";");
				}
				if(StringUtil.isNotEmpty(sb.toString())){
					vo.setSkipCondition(sb.substring(0,sb.toString().length()-1));
				}
			}

		}
		purposeVOIPage.setRecords(purposeVOS);
		return purposeVOIPage;
	}

	private CstnPurpose queryCstnPurpose(PurposeShowVO entity) {

		CstnPurpose purpose = this.getOne(Wrappers.<CstnPurpose>lambdaQuery()
				.eq(CstnPurpose::getPurposeName, entity.getPurposeName().trim()));

		return purpose;
	}
	@Override
	@Transactional
	public boolean add(PurposeShowVO entity) {

		// 唯一性校验
		if (Objects.nonNull(queryCstnPurpose(entity))) {
			throw BusinessException.create(SystemMessage.PURPOSE_NAME_CANNOT_BE_DUPLICATED);
		}

		CstnPurpose purpose = new CstnPurpose();
		BeanUtil.copyProperties(entity, purpose);
		this.save(purpose);

		//先删除
		conditionMapper.deleteConditionByRefIdAndRefType(purpose.getId(),RefTypeEnum.PURPOSE_CONDITION.getMessage());

		//选择条件保存
		List<ConditionVO> conditionVOS = entity.getSelConditionList();
		if(CollUtil.isNotEmpty(conditionVOS)){
			for(ConditionVO conditionVO:conditionVOS){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.PURPOSE_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SEL_CONDITION.getMessage());
				condition.setRefId(purpose.getId());
				condition.setOpUnit(conditionVO.getUnit());
				condition.setOpVal(conditionVO.getTargetValue());
				condition.setOp(conditionVO.getOp());
				condition.setConditionName(conditionVO.getConditionTypeName());
				condition.setMetaId(conditionVO.getConditionType());
				if(StringUtil.isNotEmpty(conditionVO.getConditionTypeName())){
					cstnConditionService.save(condition);
				}
			}
		}

		//跳过条件保存
		List<ConditionVO> skipConditions = entity.getSkipConditionList();
		if(CollUtil.isNotEmpty(skipConditions)){
			for(ConditionVO conditionVO:skipConditions){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.PURPOSE_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SKIP_CONDITION.getMessage());
				condition.setRefId(purpose.getId());
				condition.setOpUnit(conditionVO.getUnit());
				condition.setOpVal(conditionVO.getTargetValue());
				condition.setOp(conditionVO.getOp());
				condition.setConditionName(conditionVO.getConditionTypeName());
				condition.setMetaId(conditionVO.getConditionType());
				if(StringUtil.isNotEmpty(conditionVO.getConditionTypeName())){
					cstnConditionService.save(condition);
				}
			}
		}

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.ADD.getMessage());
		opr.setRefType(PURPOSE);
		opr.setRefId(purpose.getId());
		operationHistoryService.save(opr);


		return true;
	}

	@Override
	@Transactional
	public boolean remove(String id) {

		this.removeById(id);

		//删除相应的关联表
		cstnPathPurposeMapper.deletePathPurpose(Strings.EMPTY,id);
		cstnQuestionPurposeMapper.deleteQuestionPurpose(Strings.EMPTY,id);
		conditionMapper.deleteConditionByRefIdAndRefType(id,RefTypeEnum.PURPOSE_CONDITION.getMessage());

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.REMOVE.getMessage());
		opr.setRefType(PURPOSE);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	@Transactional
	public boolean update(PurposeShowVO entity) {

		//如果主目标下已经有子目标，不可以修改成子目标
		if(CommonConstants.TWO.equals(entity.getPurposeType())) {
			List<CstnPurpose> purposes = this.list(Wrappers.<CstnPurpose>lambdaQuery()
					.eq(CstnPurpose::getParentPurpose, entity.getId()));
			if (CollUtil.isNotEmpty(purposes)) {
				throw BusinessException.create(SystemMessage.PARENT_PURPOSE_ALREADY_HAS_CHILD_PURPOSE);
			}
		}

		CstnPurpose cstnPurpose = queryCstnPurpose(entity);

		// 唯一性校验
		if (Objects.nonNull(cstnPurpose) && !entity.getId().equals(cstnPurpose.getId()) ) {
			throw BusinessException.create(SystemMessage.PURPOSE_NAME_CANNOT_BE_DUPLICATED);
		}

		CstnPurpose purpose = this.getById(entity.getId());
		BeanUtil.copyProperties(entity, purpose);
		this.updateById(purpose);

		//先删除
		conditionMapper.deleteConditionByRefIdAndRefType(purpose.getId(),RefTypeEnum.PURPOSE_CONDITION.getMessage());

		//选择条件保存
		List<ConditionVO> conditionVOS = entity.getSelConditionList();
		if(CollUtil.isNotEmpty(conditionVOS)){
			for(ConditionVO conditionVO:conditionVOS){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.PURPOSE_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SEL_CONDITION.getMessage());
				condition.setRefId(purpose.getId());
				condition.setOpUnit(conditionVO.getUnit());
				condition.setOpVal(conditionVO.getTargetValue());
				condition.setOp(conditionVO.getOp());
				condition.setConditionName(conditionVO.getConditionTypeName());
				condition.setMetaId(conditionVO.getConditionType());
				if(StringUtil.isNotEmpty(conditionVO.getConditionTypeName())){
					cstnConditionService.save(condition);
				}
			}
		}

		//跳过条件保存
		List<ConditionVO> skipConditions = entity.getSkipConditionList();
		if(CollUtil.isNotEmpty(skipConditions)){
			for(ConditionVO conditionVO:skipConditions){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.PURPOSE_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SKIP_CONDITION.getMessage());
				condition.setRefId(purpose.getId());
				condition.setOpUnit(conditionVO.getUnit());
				condition.setOpVal(conditionVO.getTargetValue());
				condition.setOp(conditionVO.getOp());
				condition.setConditionName(conditionVO.getConditionTypeName());
				condition.setMetaId(conditionVO.getConditionType());
				if(StringUtil.isNotEmpty(conditionVO.getConditionTypeName())){
					cstnConditionService.save(condition);
				}
			}
		}

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.EDIT.getMessage());
		opr.setRefType(PURPOSE);
		opr.setRefId(purpose.getId());
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	public PurposeShowVO show(String id) {

		CstnPurpose purpose = this.getById(id);
		PurposeShowVO purposeShowVO = new PurposeShowVO();
		BeanUtil.copyProperties(purpose,purposeShowVO);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.PURPOSE_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			purposeShowVO.setSelConditionList(conditions);
		}

		List<ConditionVO> conditionList = conditionMapper.getConditionList(id,RefTypeEnum.PURPOSE_CONDITION.getMessage(),
				ConditionTypeEnum.SKIP_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditionList)){
			purposeShowVO.setSkipConditionList(conditionList);
		}

		return purposeShowVO;
	}


	@Override
	public PurposeShowVO edit(String id) {

		CstnPurpose purpose = this.getById(id);
		PurposeShowVO purposeShowVO = new PurposeShowVO();
		BeanUtil.copyProperties(purpose,purposeShowVO);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.PURPOSE_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			purposeShowVO.setSelConditionList(conditions);
		}

		List<ConditionVO> conditionList = conditionMapper.getConditionList(id,RefTypeEnum.PURPOSE_CONDITION.getMessage(),
				ConditionTypeEnum.SKIP_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditionList)){
			purposeShowVO.setSkipConditionList(conditionList);
		}

		return purposeShowVO;
	}

	@Override
	public PurposeShowVO preview(String id) {

		CstnPurpose purpose = this.getById(id);
		PurposeShowVO purposeShowVO = new PurposeShowVO();
		BeanUtil.copyProperties(purpose,purposeShowVO);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.PURPOSE_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			purposeShowVO.setSelConditionList(conditions);
		}

		List<ConditionVO> conditionList = conditionMapper.getConditionList(id,RefTypeEnum.PURPOSE_CONDITION.getMessage(),
				ConditionTypeEnum.SKIP_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditionList)){
			purposeShowVO.setSkipConditionList(conditionList);
		}
		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.PREVIEW.getMessage());
		opr.setRefType(PURPOSE);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return purposeShowVO;
	}

	@Override
	public List<Tree<String>> selectTree() {

		List<MetaDataVO> metaDataList = metaMapper.getMetaDataList();
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
					return treeNode;
				})
				.collect(Collectors.toList());

		return TreeUtil.build(collect, CommonConstants.ZERO);
	}


	@Override
	public List<Tree<String>> getParentPurposeList() {

		List<PurposeVO> purposeList = mapper.getParentPurposeList();
		if (CollUtil.isEmpty(purposeList)) {
			return Collections.emptyList();
		}


		List<TreeNode<String>> collect = purposeList.stream()
				.filter(purpose -> !purpose.getId().equals(purpose.getParentPurpose()))
				.map(purpose -> {
					TreeNode<String> treeNode = new TreeNode();
					treeNode.setId(purpose.getId());

					if (StringUtils.isBlank(purpose.getParentPurpose())) {
						purpose.setParentPurpose(CommonConstants.ZERO);
					}
					treeNode.setParentId(purpose.getParentPurpose());
					treeNode.setName(purpose.getPurposeName());
					if(CommonConstants.ZERO.equals(purpose.getParentPurpose())){
						treeNode.setWeight("1");
					}else{
						treeNode.setWeight("2");
					}

					Map<String, Object> extra = new HashMap<>(8);
					extra.put("purposeDesc", purpose.getPurposeDesc());
					extra.put("isLoop", purpose.getIsLoop());
					treeNode.setExtra(extra);

					return treeNode;
				})
				.collect(Collectors.toList());

		return TreeUtil.build(collect, CommonConstants.ZERO);
	}

}
