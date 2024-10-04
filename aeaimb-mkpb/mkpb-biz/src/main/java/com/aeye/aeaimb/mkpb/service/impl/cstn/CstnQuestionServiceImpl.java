package com.aeye.aeaimb.mkpb.service.impl.cstn;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.aeye.aeaimb.common.core.constant.enums.ConditionTypeEnum;
import com.aeye.aeaimb.common.core.constant.enums.OpEnum;
import com.aeye.aeaimb.common.core.constant.enums.OperationEnum;
import com.aeye.aeaimb.common.core.constant.enums.RefTypeEnum;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnQuestionDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.*;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestion;
import com.aeye.aeaimb.mkpb.entity.cstn.CstnQuestion;
import com.aeye.aeaimb.mkpb.mapper.cstn.*;
import com.aeye.aeaimb.mkpb.service.cstn.*;
import com.aeye.aeaimb.mkpb.vo.cstn.*;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * [问诊]问题 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Service
@DS("wmkg")
public class CstnQuestionServiceImpl extends ServiceImpl<CstnQuestionMapper, CstnQuestion> implements CstnQuestionService {

	public static final String QUESTION = "question";

	@Resource
	private CstnOperationHistoryService operationHistoryService;

	@Resource
	private CstnQuestionPurposeService cstnQuestionPurposeService;

	@Resource
	private CstnQuestionMapper mapper;

	@Resource
	private CstnQuestionPurposeMapper questionPurposeMapper;

	@Resource
	private CstnConditionMapper conditionMapper;

	@Resource
	private CstnQuestionOptionMapper cstnQuestionOptionMapper;

	@Resource
	private CstnConditionService cstnConditionService;

	@Resource
	private  CstnQuestionPurposeMapper cstnQuestionPurposeMapper;

	@Resource
	private CstnQuestionOptionService cstnQuestionOptionService;

	@Override
	public IPage<CstnQuestionVO> getCstnQuestionPageList(Page<CstnQuestion> page, CstnQuestionDTO dto) {

		IPage iPage = mapper.getCstnQuestionPageList(page, dto);
		List<CstnQuestionVO> pathVOS = iPage.getRecords();
		for(CstnQuestionVO questionVO:pathVOS){
			List<ConditionVO> conditions = conditionMapper.getConditionList(questionVO.getId(), RefTypeEnum.QUESTION_CONDITION.getMessage(),
					ConditionTypeEnum.SEL_CONDITION.getMessage());
			if(CollUtil.isNotEmpty(conditions)){
				StringBuilder sb = new StringBuilder();
				for(ConditionVO conditionVO:conditions){
					sb.append(conditionVO.getConditionTypeName()+ 
							(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())+
							conditionVO.getTargetValue()+
							(StringUtil.isNotEmpty(conditionVO.getUnit())?conditionVO.getUnit():Strings.EMPTY)+";");
				}
				if(StringUtil.isNotEmpty(sb.toString())){
					questionVO.setSelCondition(sb.substring(0,sb.toString().length()-1));
				}
			}

			List<ConditionVO> conditionList = conditionMapper.getConditionList(questionVO.getId(), RefTypeEnum.QUESTION_CONDITION.getMessage(),
					ConditionTypeEnum.SKIP_CONDITION.getMessage());
			if(CollUtil.isNotEmpty(conditionList)){
				StringBuilder sb = new StringBuilder();
				for(ConditionVO conditionVO:conditionList){
					sb.append(conditionVO.getConditionTypeName()+
							(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())
							+conditionVO.getTargetValue()+
							(StringUtil.isNotEmpty(conditionVO.getUnit())?conditionVO.getUnit():Strings.EMPTY)+";");
				}
				if(StringUtil.isNotEmpty(sb.toString())){
					questionVO.setSkipCondition(sb.substring(0,sb.toString().length()-1));
				}
			}

			//选项赋值
			List<CstnQuestionOption> questionOptions = cstnQuestionOptionMapper.getQuestionOptionList(questionVO.getId());
			if(CollUtil.isNotEmpty(questionOptions)){
				String optionStr = questionOptions.stream()
						.map(CstnQuestionOption::getOptionName)
						.collect(Collectors.joining(","));
				questionVO.setQaSelect(optionStr);
			}

		}
		iPage.setRecords(pathVOS);
		return iPage;
	}

	private CstnQuestion queryCstnQuestion(QuestionVO entity) {

		CstnQuestion question = this.getOne(Wrappers.<CstnQuestion>lambdaQuery()
				.eq(CstnQuestion::getQaTitle, entity.getQaTitle().trim()));

		return question;
	}

	public boolean hasDuplicates(String[] array) {
		Set<String> set = new HashSet<>();
		for (String element : array) {
			if (!set.add(element)) {
				return true; // 找到重复
			}
		}
		return false; // 没有重复
	}

	@Override
	@Transactional
	public boolean add(QuestionVO entity) {

		// 唯一性校验
		if (Objects.nonNull(queryCstnQuestion(entity))) {
			throw BusinessException.create(SystemMessage.QUESTION_CANNOT_BE_DUPLICATED);
		}

		CstnQuestion cstnQuestion = new CstnQuestion();
		BeanUtil.copyProperties(entity,cstnQuestion);

		this.save(cstnQuestion);

		//先删除
		cstnQuestionOptionMapper.deleteQuestionOptionList(cstnQuestion.getId());

		String qaSelect = entity.getQaSelect();
		if(StringUtil.isNotEmpty(qaSelect)){
			String[] sels = qaSelect.split(",");
			if(hasDuplicates(sels)){
				throw BusinessException.create(SystemMessage.QUESTION_OPTION_CANNOT_BE_DUPLICATED);
			}
			for (String sel : sels) {
				CstnQuestionOption cstnQuestionOption = new CstnQuestionOption();
				cstnQuestionOption.setOptionName(sel);
				cstnQuestionOption.setQaId(cstnQuestion.getId());
				cstnQuestionOptionService.save(cstnQuestionOption);
			}
		}

		//先删除
		conditionMapper.deleteConditionByRefIdAndRefType(cstnQuestion.getId(),RefTypeEnum.QUESTION_CONDITION.getMessage());

		//选择条件保存
		List<ConditionVO> conditionVOS = entity.getSelConditionList();
		if(CollUtil.isNotEmpty(conditionVOS)){
			for(ConditionVO conditionVO:conditionVOS){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.QUESTION_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SEL_CONDITION.getMessage());
				condition.setOpUnit(conditionVO.getUnit());
				condition.setRefId(cstnQuestion.getId());
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
				condition.setRefType(RefTypeEnum.QUESTION_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SKIP_CONDITION.getMessage());
				condition.setRefId(cstnQuestion.getId());
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

		CstnQuestionPurpose questionPurpose = new CstnQuestionPurpose();
		questionPurpose.setQaId(cstnQuestion.getId());
		questionPurpose.setPurposeId(entity.getPurposeId());
		cstnQuestionPurposeService.save(questionPurpose);

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.ADD.getMessage());
		opr.setRefType(QUESTION);
		opr.setRefId(cstnQuestion.getId());
		operationHistoryService.save(opr);


		return true;
	}

	@Override
	@Transactional
	public boolean remove(String id) {

		this.removeById(id);

		//删除相应的关联表
		cstnQuestionOptionMapper.deleteQuestionOptionList(id);
		cstnQuestionPurposeMapper.deleteQuestionPurpose(id,Strings.EMPTY);
		conditionMapper.deleteConditionByRefIdAndRefType(id,RefTypeEnum.QUESTION_CONDITION.getMessage());

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.REMOVE.getMessage());
		opr.setRefType(QUESTION);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	@Transactional
	public boolean update(QuestionVO questionVO) {

		CstnQuestion question =queryCstnQuestion(questionVO);

		// 唯一性校验
		if ( Objects.nonNull(question) && !question.getId().equals(questionVO.getId())) {
			throw BusinessException.create(SystemMessage.QUESTION_CANNOT_BE_DUPLICATED);
		}

		CstnQuestion cstnQuestion = this.getById(questionVO.getId());
		BeanUtil.copyProperties(questionVO,cstnQuestion);

		this.updateById(cstnQuestion);

		//先删除
		cstnQuestionOptionMapper.deleteQuestionOptionList(cstnQuestion.getId());

		String qaSelect = questionVO.getQaSelect();
		if(StringUtil.isNotEmpty(qaSelect)){
			String[] sels = qaSelect.split(",");
			if(hasDuplicates(sels)){
				throw BusinessException.create(SystemMessage.QUESTION_OPTION_CANNOT_BE_DUPLICATED);
			}
			for (String sel : sels) {
				CstnQuestionOption cstnQuestionOption = new CstnQuestionOption();
				cstnQuestionOption.setOptionName(sel);
				cstnQuestionOption.setQaId(cstnQuestion.getId());
				cstnQuestionOptionService.save(cstnQuestionOption);
			}
		}

		//先删除
		conditionMapper.deleteConditionByRefIdAndRefType(cstnQuestion.getId(),RefTypeEnum.QUESTION_CONDITION.getMessage());

		//选择条件保存
		List<ConditionVO> conditionVOS = questionVO.getSelConditionList();
		if(CollUtil.isNotEmpty(conditionVOS)){
			for(ConditionVO conditionVO:conditionVOS){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.QUESTION_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SEL_CONDITION.getMessage());
				condition.setRefId(cstnQuestion.getId());
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
		List<ConditionVO> skipConditions = questionVO.getSkipConditionList();
		if(CollUtil.isNotEmpty(skipConditions)){
			for(ConditionVO conditionVO:skipConditions){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.QUESTION_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SKIP_CONDITION.getMessage());
				condition.setRefId(cstnQuestion.getId());
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

		List<CstnQuestionPurpose> questionPurposes = questionPurposeMapper.getQuestionPurposeList(questionVO.getId(),questionVO.getPurposeId());
        if(CollUtil.isNotEmpty(questionPurposes)){
			CstnQuestionPurpose purpose = new CstnQuestionPurpose();
			purpose.setPurposeId(questionVO.getPurposeId());
			cstnQuestionPurposeService.updateById(purpose);
		}else{
			CstnQuestionPurpose questionPurpose = new CstnQuestionPurpose();
			questionPurpose.setQaId(questionVO.getId());
			questionPurpose.setPurposeId(questionVO.getPurposeId());
			cstnQuestionPurposeService.save(questionPurpose);
		}

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.EDIT.getMessage());
		opr.setRefType(QUESTION);
		opr.setRefId(questionVO.getId());
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	public QuestionVO show(String id) {

		CstnQuestion question = this.getById(id);
		QuestionVO questionVO = new QuestionVO();
		BeanUtil.copyProperties(question,questionVO);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.QUESTION_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			questionVO.setSelConditionList(conditions);
		}

		List<ConditionVO> conditionList = conditionMapper.getConditionList(id,RefTypeEnum.QUESTION_CONDITION.getMessage(),
				ConditionTypeEnum.SKIP_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditionList)){
			questionVO.setSkipConditionList(conditionList);
		}

		//选项赋值
		List<CstnQuestionOption> questionOptions = cstnQuestionOptionMapper.getQuestionOptionList(id);
		if(CollUtil.isNotEmpty(questionOptions)){
			String optionStr = questionOptions.stream()
					.map(CstnQuestionOption::getOptionName)
					.collect(Collectors.joining(","));
			questionVO.setQaSelect(optionStr);
		}

		//目标反显
		List<CstnQuestionPurpose> questionPurposes = cstnQuestionPurposeMapper.getQuestionPurposeList(id,Strings.EMPTY);
		if(CollUtil.isNotEmpty(questionPurposes)){
			CstnQuestionPurpose questionPurpose = questionPurposes.get(0);
			questionVO.setPurposeId(questionPurpose.getPurposeId());
		}

		return questionVO;
	}

	@Override
	public QuestionVO edit(String id) {

		CstnQuestion question = this.getById(id);
		QuestionVO questionVO = new QuestionVO();
		BeanUtil.copyProperties(question,questionVO);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.QUESTION_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			questionVO.setSelConditionList(conditions);
		}

		List<ConditionVO> conditionList = conditionMapper.getConditionList(id,RefTypeEnum.QUESTION_CONDITION.getMessage(),
				ConditionTypeEnum.SKIP_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditionList)){
			questionVO.setSkipConditionList(conditionList);
		}

		//选项赋值
		List<CstnQuestionOption> questionOptions = cstnQuestionOptionMapper.getQuestionOptionList(id);
		if(CollUtil.isNotEmpty(questionOptions)){
			String optionStr = questionOptions.stream()
					.map(CstnQuestionOption::getOptionName)
					.collect(Collectors.joining(","));
			questionVO.setQaSelect(optionStr);
		}

		//目标反显
		List<CstnQuestionPurpose> questionPurposes = cstnQuestionPurposeMapper.getQuestionPurposeList(id,Strings.EMPTY);
		if(CollUtil.isNotEmpty(questionPurposes)){
			CstnQuestionPurpose questionPurpose = questionPurposes.get(0);
			questionVO.setPurposeId(questionPurpose.getPurposeId());
		}

		return questionVO;
	}

	@Override
	public QuestionVO preview(String id) {

		CstnQuestion question = this.getById(id);
		QuestionVO questionVO = new QuestionVO();
		BeanUtil.copyProperties(question,questionVO);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.QUESTION_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			questionVO.setSelConditionList(conditions);
		}

		List<ConditionVO> conditionList = conditionMapper.getConditionList(id,RefTypeEnum.QUESTION_CONDITION.getMessage(),
				ConditionTypeEnum.SKIP_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditionList)){
			questionVO.setSkipConditionList(conditionList);
		}

		//选项赋值
		List<CstnQuestionOption> questionOptions = cstnQuestionOptionMapper.getQuestionOptionList(id);
		if(CollUtil.isNotEmpty(questionOptions)){
			String optionStr = questionOptions.stream()
					.map(CstnQuestionOption::getOptionName)
					.collect(Collectors.joining(","));
			questionVO.setQaSelect(optionStr);
		}

		//目标反显
		List<CstnQuestionPurpose> questionPurposes = cstnQuestionPurposeMapper.getQuestionPurposeList(id,Strings.EMPTY);
		if(CollUtil.isNotEmpty(questionPurposes)){
			CstnQuestionPurpose questionPurpose = questionPurposes.get(0);
			questionVO.setPurposeId(questionPurpose.getPurposeId());
		}

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.PREVIEW.getMessage());
		opr.setRefType(QUESTION);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return questionVO;
	}
	
}
