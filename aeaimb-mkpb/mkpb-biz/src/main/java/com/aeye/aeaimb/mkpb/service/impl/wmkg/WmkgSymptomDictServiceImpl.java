package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.common.core.constant.enums.CommonEnum;
import com.aeye.aeaimb.common.core.exception.BusinessException;
import com.aeye.aeaimb.common.core.exception.SystemMessage;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgSymptomDict;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgSymptomDictMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgSymptomDictService;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgSymptomDictVO;
import com.aeye.cdss.admin.api.dto.dict.BaseSymptomDictPageDTO;
import com.aeye.cdss.admin.api.dto.dict.BaseSymptomDictQueryDTO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [西医]症状字典(WmkgSymptomDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-24 13:38:21
 */
@Service("wmkgSymptomDictService")
@DS("wmkg")
public class WmkgSymptomDictServiceImpl extends ServiceImpl<WmkgSymptomDictMapper, WmkgSymptomDict> implements WmkgSymptomDictService {
	@Resource
	private WmkgSymptomDictMapper wmkgSymptomDictMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateSymptomDict(WmkgSymptomDict entity, String addFlag) {
		//校验唯一性
		validateSymptomCode(entity,addFlag);

		this.saveOrUpdate(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeSymptomDict(List<String> ids) {
		//该症状已匹配知识库数据，不能删除

		this.removeByIds(ids);
	}

	public void validateSymptomCode(WmkgSymptomDict entity, String addFlag) {
		if (CommonEnum.YesOrNoStr.YES.getType().equals(addFlag)) {
			long count = this.count(Wrappers.<WmkgSymptomDict>lambdaQuery()
					.eq(WmkgSymptomDict::getSymptomCode, entity.getSymptomCode()));
			if (count > 0) {
				throw BusinessException.create(SystemMessage.SYMPTOM_DICT_CODE_VALIDATE);
			}

			long acount = this.count(Wrappers.<WmkgSymptomDict>lambdaQuery()
					.eq(WmkgSymptomDict::getSymptomName, entity.getSymptomName()));
			if (acount > 0) {
				throw BusinessException.create(SystemMessage.SYMPTOM_DICT_NAME_VALIDATE);
			}
		} else {
			long count = this.count(Wrappers.<WmkgSymptomDict>lambdaQuery()
					.eq(WmkgSymptomDict::getSymptomCode, entity.getSymptomCode()));
			if (count <= 0) {
				throw BusinessException.create(SystemMessage.SYMPTOM_DICT_CODE_NO_VALIDATE);
			}

			long acount = this.count(Wrappers.<WmkgSymptomDict>lambdaQuery()
					.eq(WmkgSymptomDict::getSymptomName, entity.getSymptomName())
					.ne(WmkgSymptomDict::getSymptomCode, entity.getSymptomCode()));
			if (acount > 0) {
				throw BusinessException.create(SystemMessage.SYMPTOM_DICT_NAME_VALIDATE);
			}
		}

	}


	@Override
	public IPage<WmkgSymptomDictVO> findBaseSymptomDictPageList(Page<WmkgSymptomDict> page, BaseSymptomDictPageDTO dto) {
		LambdaQueryWrapper<WmkgSymptomDict> queryWrapper = Wrappers.<WmkgSymptomDict>lambdaQuery()
				.eq(StringUtils.isNotBlank(dto.getSymptomType()), WmkgSymptomDict::getSymptomType, dto.getSymptomType());
		if (StringUtils.isNotBlank(dto.getSymptomName())){
			queryWrapper.and(t -> t.like(WmkgSymptomDict::getSymptomCode, dto.getSymptomName())
					.or().like(WmkgSymptomDict::getSymptomName, dto.getSymptomName()));
		}
		queryWrapper.orderByDesc(WmkgSymptomDict::getCreateTime);

		Page<WmkgSymptomDict> diseaseDictPage = this.page(page, queryWrapper);
		return diseaseDictPage.convert(item-> BeanConvertUtil.convertBean(item, WmkgSymptomDictVO.class));
	}

	@Override
	public List<WmkgSymptomDictVO> findBaseSymptomDictList(BaseSymptomDictQueryDTO dto) {
		LambdaQueryWrapper<WmkgSymptomDict> queryWrapper = Wrappers.<WmkgSymptomDict>lambdaQuery()
				.like(StringUtils.isNotBlank(dto.getSymptomName()), WmkgSymptomDict::getSymptomName, dto.getSymptomName());
		if (StringUtils.isBlank(dto.getSymptomName())){
			queryWrapper.last("LIMIT 100");
		}
		queryWrapper.orderByDesc(WmkgSymptomDict::getCreateTime);

		List<WmkgSymptomDict> list = this.list(queryWrapper);

		List<WmkgSymptomDictVO> baseSymptomDictVOS = BeanConvertUtil.convertListBean(list, WmkgSymptomDictVO.class);
		List<String> symptomCodeList = list.stream().map(WmkgSymptomDict::getSymptomCode).collect(Collectors.toList());
		//存在知识库字典映射的数据
//		LambdaQueryWrapper<BaseDictKgMapping> in = Wrappers.<BaseDictKgMapping>lambdaQuery().in(BaseDictKgMapping::getMediDictCode, symptomCodeList);
//		List<BaseDictKgMapping> dictKgMappingList = baseDictKgMappingService.list(in);
//		List<String> mappedMediDictCode = dictKgMappingList.stream().map(BaseDictKgMapping::getMediDictCode).collect(Collectors.toList());
//		baseSymptomDictVOS.forEach(item->{
//			if (CollUtil.isNotEmpty(mappedMediDictCode)
//					&& mappedMediDictCode.contains(item.getSymptomCode())){
//				item.setMappingFlag(CommonEnum.YesOrNoStr.YES.getType());
//			}else {
//				item.setMappingFlag(CommonEnum.YesOrNoStr.NO.getType());
//			}
//		});

		return baseSymptomDictVOS;
	}
}
