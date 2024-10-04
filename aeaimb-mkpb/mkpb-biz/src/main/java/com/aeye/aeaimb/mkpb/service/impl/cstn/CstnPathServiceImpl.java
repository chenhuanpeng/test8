package com.aeye.aeaimb.mkpb.service.impl.cstn;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.aeye.aeaimb.common.core.constant.CommonConstants;
import com.aeye.aeaimb.common.core.constant.enums.*;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.common.core.util.CounterUtil;
import com.aeye.aeaimb.mkpb.dto.cstn.CstnPathDTO;
import com.aeye.aeaimb.mkpb.entity.cstn.*;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDeptDict;
import com.aeye.aeaimb.mkpb.mapper.cstn.*;
import com.aeye.aeaimb.mkpb.service.cstn.*;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDeptDictService;
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
 * [问诊]路径 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Service("cstnPathService")
@DS("wmkg")
public class CstnPathServiceImpl extends ServiceImpl<CstnPathMapper, CstnPath> implements CstnPathService {

	public static final String PATH = "path";

	@Resource
	private CstnPathMapper mapper;

	@Resource
	private StatusService statusService;

	@Resource
	private CstnPathDeptService cstnPathDeptService;

	@Resource
	private CstnOperationHistoryService operationHistoryService;

	@Resource
	private WmkgDeptDictService dictService;

	@Resource
	private CstnPurposeMapper purposeMapper;

	@Resource CstnPathPurposeService cstnPathPurposeService;

	@Resource
	private CstnPathPurposeMapper cstnPathPurposeMapper;

	@Resource
	private CstnConditionMapper conditionMapper;

	@Resource
	private CstnPathDeptMapper cstnPathDeptMapper;


	@Resource
	private CstnConditionService cstnConditionService;


	@Override
	public IPage<CstnPathVO> getCstnPathPageList(Page<CstnPath> page, CstnPathDTO dto) {

		IPage iPage = mapper.getCstnPathPageList(page, dto);
		List<CstnPathVO> pathVOS = iPage.getRecords();
		for(CstnPathVO pathVO:pathVOS){
			List<ConditionVO> conditions = conditionMapper.getConditionList(pathVO.getId(),RefTypeEnum.PATH_CONDITION.getMessage(),
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
					pathVO.setSelCondition(sb.substring(0,sb.toString().length()-1));
				}
			}
            //科室名称反显
			List<CstnPathDept> pathDepts = cstnPathDeptMapper.getCstnPathDeptList(pathVO.getId());
			if(CollUtil.isNotEmpty(pathDepts)){
				String name = pathDepts.stream()
						.map(CstnPathDept::getDeptName)
						.collect(Collectors.joining(","));
				pathVO.setDepartment(name);
			}

			Long qty = cstnPathPurposeMapper.getPurposeQtyByPathId(pathVO.getId());
			pathVO.setQty(qty);
		}
		iPage.setRecords(pathVOS);
		return iPage;
	}

	private CstnPath queryCstnPath(PathVO entity) {
		// 根据路径名称查询路径
		CstnPath path = this.getOne(Wrappers.<CstnPath>lambdaQuery()
				.eq(CstnPath::getPathName, entity.getPathName().trim()));

		return path;
	}

	@Override
	@Transactional
	public boolean add(PathVO entity) {

		// 唯一性校验
		if (Objects.nonNull(queryCstnPath(entity))) {
			throw BusinessException.create(SystemMessage.PATH_NAME_CANNOT_BE_DUPLICATED);
		}

		CstnPath path = new CstnPath();
		BeanUtil.copyProperties(entity, path);
		path.setPathStatus(OperationStatusEnum.WAIT_SUBMIT.getValue());
		this.save(path);

		//先删除
		cstnPathDeptMapper.deleteCstnPathDeptList(path.getId());

		String departmentIdStr = entity.getDepartmentId();

		if(StringUtil.isNotEmpty(departmentIdStr)){
			String[] departmentIds = departmentIdStr.split("\\s*,\\s*");

			for (String departmentId : departmentIds) {
				WmkgDeptDict deptDict = dictService.queryDeptDict(departmentId);
				CstnPathDept dept = new CstnPathDept();
				dept.setDeptCode(deptDict.getDeptCode());
				dept.setDeptName(deptDict.getDeptName());
				dept.setPathId(path.getId());
				cstnPathDeptService.save(dept);
			}
		}


		//先删除
		conditionMapper.deleteConditionByRefIdAndRefType(path.getId(),RefTypeEnum.PATH_CONDITION.getMessage());

		//条件保存
		List<ConditionVO> conditionVOS = entity.getSelConditionList();
		if(CollUtil.isNotEmpty(conditionVOS)){
			for(ConditionVO conditionVO:conditionVOS){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.PATH_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SEL_CONDITION.getMessage());
				condition.setRefId(path.getId());
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
		opr.setRefType(PATH);
		opr.setRefId(path.getId());
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	@Transactional
	public boolean remove(String id) {

		this.removeById(id);

		//删除相应的关联表
		cstnPathPurposeMapper.deleteByPathId(id);
		cstnPathDeptMapper.deleteCstnPathDeptList(id);
		conditionMapper.deleteConditionByRefIdAndRefType(id,RefTypeEnum.PATH_CONDITION.getMessage());


		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.REMOVE.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	@Transactional
	public boolean update(PathVO entity) {

		CstnPath cstnPath = queryCstnPath(entity);

		// 唯一性校验
		if (Objects.nonNull(cstnPath) && !entity.getId().equals(cstnPath.getId())) {
			throw BusinessException.create(SystemMessage.PATH_NAME_CANNOT_BE_DUPLICATED);
		}

		CstnPath path = this.getById(entity.getId());
		BeanUtil.copyProperties(entity, path);
		this.updateById(path);

		//先删除
		cstnPathDeptMapper.deleteCstnPathDeptList(path.getId());

		String departmentIdStr = entity.getDepartmentId();
		if(StringUtil.isNotEmpty(departmentIdStr)){
			String[] departmentIds = departmentIdStr.split("\\s*,\\s*");

			for (String departmentId : departmentIds) {
				WmkgDeptDict deptDict = dictService.queryDeptDict(departmentId);
				CstnPathDept dept = new CstnPathDept();
				dept.setDeptCode(deptDict.getDeptCode());
				dept.setDeptName(deptDict.getDeptName());
				dept.setPathId(path.getId());
				cstnPathDeptService.save(dept);
			}
		}


		//先删除
		conditionMapper.deleteConditionByRefIdAndRefType(path.getId(),RefTypeEnum.PATH_CONDITION.getMessage());

		//条件保存
		List<ConditionVO> conditionVOS = entity.getSelConditionList();
		if(CollUtil.isNotEmpty(conditionVOS)){
			for(ConditionVO conditionVO:conditionVOS){

				CstnCondition condition = new CstnCondition();
				condition.setRefType(RefTypeEnum.PATH_CONDITION.getMessage());
				condition.setConditionType(ConditionTypeEnum.SEL_CONDITION.getMessage());
				condition.setRefId(path.getId());
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
		opr.setRefType(PATH);
		opr.setRefId(entity.getId());
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	public PathVO edit(String id) {

		PathVO pathVO = mapper.getPathList(id).get(0);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.PATH_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			pathVO.setSelConditionList(conditions);
		}

		//科室编码和名称反显
		List<CstnPathDept> pathDepts = cstnPathDeptMapper.getCstnPathDeptList(id);
		if(CollUtil.isNotEmpty(pathDepts)){
			String name = pathDepts.stream()
					.map(CstnPathDept::getDeptName)
					.collect(Collectors.joining(","));
			String deptIds = pathDepts.stream()
					.map(CstnPathDept::getDeptCode)
					.collect(Collectors.joining(","));
			pathVO.setDepartment(name);
			pathVO.setDepartmentId(deptIds);
		}

		return pathVO;
	}

	@Override
	public PathVO show(String id) {

		PathVO pathVO = mapper.getPathList(id).get(0);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.PATH_CONDITION.getMessage(),
				ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			pathVO.setSelConditionList(conditions);
		}

		//科室编码和名称反显
		List<CstnPathDept> pathDepts = cstnPathDeptMapper.getCstnPathDeptList(id);
		if(CollUtil.isNotEmpty(pathDepts)){
			String name = pathDepts.stream()
					.map(CstnPathDept::getDeptName)
					.collect(Collectors.joining(","));
			String deptIds = pathDepts.stream()
					.map(CstnPathDept::getDeptCode)
					.collect(Collectors.joining(","));
			pathVO.setDepartment(name);
			pathVO.setDepartmentId(deptIds);
		}


		return pathVO;
	}

	@Override
	public PathVO preview(String id) {

		PathVO pathVO = mapper.getPathList(id).get(0);

		List<ConditionVO> conditions = conditionMapper.getConditionList(id,RefTypeEnum.PATH_CONDITION.getMessage(),ConditionTypeEnum.SEL_CONDITION.getMessage());
		if(CollUtil.isNotEmpty(conditions)){
			pathVO.setSelConditionList(conditions);
		}

		//科室编码和名称反显
		List<CstnPathDept> pathDepts = cstnPathDeptMapper.getCstnPathDeptList(id);
		if(CollUtil.isNotEmpty(pathDepts)){
			String name = pathDepts.stream()
					.map(CstnPathDept::getDeptName)
					.collect(Collectors.joining(","));
			String deptIds = pathDepts.stream()
					.map(CstnPathDept::getDeptCode)
					.collect(Collectors.joining(","));
			pathVO.setDepartment(name);
			pathVO.setDepartmentId(deptIds);
		}

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.PREVIEW.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return pathVO;
	}

	@Override
	public boolean submit(String id) {

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.SUBMIT.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);

		statusService.updateStatus(OperationStatusEnum.WAIT_AUDIT.getValue(), opr);

		return true;
	}

	@Override
	public boolean returnEdit(String id) {

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.RETURN_EDIT.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);

		statusService.updateStatus(OperationStatusEnum.WAIT_SUBMIT.getValue(), opr);

		return true;
	}

	@Override
	@Transactional
	public boolean relatePurpose(PathRelatePurposeVO relatePurposeVO) {

		String pathId = relatePurposeVO.getPathId();
		cstnPathPurposeMapper.deleteByPathId(pathId);
		List<TreeObj> treeNodes = relatePurposeVO.getData();
		if(CollUtil.isNotEmpty(treeNodes)){
			int count =0;
			for(TreeObj tree : treeNodes){
				++count;
				CstnPathPurpose pathPurpose = new CstnPathPurpose();
				pathPurpose.setPurposeId(tree.getId());
				pathPurpose.setPathId(pathId);

				pathPurpose.setSort(count);
				//01 是  02 否
				pathPurpose.setLoopFlag(tree.getIsLoop());
				cstnPathPurposeService.save(pathPurpose);


				List<TreeChildNode> childrens = tree.getChildren();
				if(CollUtil.isNotEmpty(childrens)){
					for(TreeChildNode child:childrens){

						++count;

						CstnPathPurpose purpose = new CstnPathPurpose();
						purpose.setPurposeId(child.getId());
						purpose.setPathId(pathId);

						purpose.setSort(count);

						//01 是  02 否
						purpose.setLoopFlag(child.getIsLoop());
						cstnPathPurposeService.save(purpose);

					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean copy(String id) {

		CstnPath path = this.getById(id);
		CstnPath newPath = new CstnPath();
		BeanUtil.copyProperties(path, newPath);
		newPath.setId(Strings.EMPTY);
		newPath.setPathName(path.getPathName() + CounterUtil.getNextNumber());
		this.save(newPath);

		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.COPY.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);
		operationHistoryService.save(opr);

		return true;
	}

	@Override
	public boolean online(String id) {
		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.ONLINE.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);

		statusService.updateStatus(OperationStatusEnum.ALREADY_ONLINE.getValue(), opr);

		return true;
	}

	@Override
	public boolean offline(String id) {
		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(Strings.EMPTY);
		opr.setOpAction(OperationEnum.OFFLINE.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(id);

		statusService.updateStatus(OperationStatusEnum.ALREADY_OFFLINE.getValue(), opr);

		return true;
	}

	@Override
	public boolean batchOffline(String[] ids) {

		for (String id : ids) {
			this.offline(id);
		}

		return true;
	}

	@Override
	public boolean batchOnline(String[] ids) {

		for (String id : ids) {
			this.online(id);
		}

		return true;
	}

	@Override
	public boolean batchAudit(PathAuditVO auditVO) {

		String[] ids = auditVO.getIds();
		for (String id : ids) {
			auditVO.setId(id);
			audit(auditVO);
		}

		return true;
	}

	@Override
	public boolean audit(PathAuditVO auditVO) {
		CstnOperationHistory opr = new CstnOperationHistory();
		opr.setOpContent(auditVO.getContent());
		opr.setOpAction(OperationEnum.AUDIT.getMessage());
		opr.setRefType(PATH);
		opr.setRefId(auditVO.getId());

		if (AuditStatusEnum.AUDIT_PASS.getValue().equals(auditVO.getAuditStatus())) {
			statusService.updateStatus(OperationStatusEnum.WAIT_ONLINE.getValue(), opr);
		} else {
			statusService.updateStatus(OperationStatusEnum.AUDIT_FAILED.getValue(), opr);
		}

		return true;
	}

	@Override
	public List<Tree<String>> selectTree() {

		List<PurposeVO> purposeList = purposeMapper.getPurposeList();
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

					List<ConditionVO> conditions = conditionMapper.getConditionList(purpose.getId(), RefTypeEnum.PURPOSE_CONDITION.getMessage(),
							ConditionTypeEnum.SEL_CONDITION.getMessage());
					if(CollUtil.isNotEmpty(conditions)){
						StringBuilder sb = new StringBuilder();
						for(ConditionVO conditionVO:conditions){
							sb.append(conditionVO.getConditionTypeName()
									+(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())
									+conditionVO.getTargetValue()+";");
						}
						if(StringUtil.isNotEmpty(sb.toString())){
							extra.put("selCondition", sb.substring(0,sb.toString().length()-1));
						}
					}

					List<ConditionVO> conditionList = conditionMapper.getConditionList(purpose.getId(), RefTypeEnum.PURPOSE_CONDITION.getMessage(),
							ConditionTypeEnum.SKIP_CONDITION.getMessage());
					if(CollUtil.isNotEmpty(conditionList)){
						StringBuilder sb = new StringBuilder();
						for(ConditionVO conditionVO:conditionList){
							sb.append(conditionVO.getConditionTypeName()+
									(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())+
									conditionVO.getTargetValue()+";");
						}
						if(StringUtil.isNotEmpty(sb.toString())){
							extra.put("skipCondition", sb.substring(0,sb.toString().length()-1));
						}
					}

					extra.put("isLoop", purpose.getIsLoop());
					treeNode.setExtra(extra);

					return treeNode;
				})
				.collect(Collectors.toList());

		return TreeUtil.build(collect, CommonConstants.ZERO);
	}

	@Override
	public List<Tree<String>> queryAllDepartment() {

		List<WmkgDeptDict> deptDicts = dictService.list(Wrappers.emptyWrapper());

		if (CollUtil.isEmpty(deptDicts)) {
			return Collections.emptyList();
		}


		List<TreeNode<String>> collect = deptDicts.stream()
				.filter(meta -> !meta.getDeptCode().equals(meta.getParentCode()))
				.map(meta -> {
					TreeNode<String> treeNode = new TreeNode();
					treeNode.setId(meta.getDeptCode());

					if (StringUtils.isBlank(meta.getParentCode())) {
						meta.setParentCode(CommonConstants.ZERO);
					}
					treeNode.setParentId(meta.getParentCode());
					treeNode.setName(meta.getDeptName());
					return treeNode;
				})
				.collect(Collectors.toList());

		return TreeUtil.build(collect, CommonConstants.ZERO);
	}


	@Override
	public List<Tree<String>> getRelatePurposeList(String pathId) {

		List<PurposeVO> purposeList = purposeMapper.getRelatePurposeList(pathId);
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

					List<ConditionVO> conditions = conditionMapper.getConditionList(purpose.getId(), RefTypeEnum.PURPOSE_CONDITION.getMessage(),
							ConditionTypeEnum.SEL_CONDITION.getMessage());
					if(CollUtil.isNotEmpty(conditions)){
						StringBuilder sb = new StringBuilder();
						for(ConditionVO conditionVO:conditions){
							sb.append(conditionVO.getConditionTypeName()+
									(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())
									+conditionVO.getTargetValue()+";");
						}
						if(StringUtil.isNotEmpty(sb.toString())){
							extra.put("selCondition", sb.substring(0,sb.toString().length()-1));
						}
					}

					List<ConditionVO> conditionList = conditionMapper.getConditionList(purpose.getId(), RefTypeEnum.PURPOSE_CONDITION.getMessage(),
							ConditionTypeEnum.SKIP_CONDITION.getMessage());
					if(CollUtil.isNotEmpty(conditionList)){
						StringBuilder sb = new StringBuilder();
						for(ConditionVO conditionVO:conditionList){
							sb.append(conditionVO.getConditionTypeName()+
									(Objects.isNull(OpEnum.getByValue(conditionVO.getOp()))?Strings.EMPTY:OpEnum.getByValue(conditionVO.getOp()).getMessage())
									+conditionVO.getTargetValue()+";");
						}
						if(StringUtil.isNotEmpty(sb.toString())){
							extra.put("skipCondition", sb.substring(0,sb.toString().length()-1));
						}
					}

					extra.put("isLoop", purpose.getIsLoop());
					treeNode.setExtra(extra);

					return treeNode;
				})
				.collect(Collectors.toList());

		return TreeUtil.build(collect, CommonConstants.ZERO);
	}


}
