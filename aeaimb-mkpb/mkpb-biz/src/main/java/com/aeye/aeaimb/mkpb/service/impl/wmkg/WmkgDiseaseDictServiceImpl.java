package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.constant.enums.CommonEnum;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDiseaseDict;
import com.aeye.aeaimb.mkpb.entity.wmkg.po.WmkgDiseaseDictPO;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgDiseaseDictMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDiseaseDictService;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgDiseaseDictVO;
import com.aeye.cdss.admin.api.dto.dict.BaseDiseaseDictPageDTO;
import com.aeye.cdss.admin.api.dto.dict.QueryBaseDiseaseDictDTO;
import com.aeye.cdss.admin.api.entity.BaseDictKgMapping;
import com.aeye.cdss.admin.api.entity.BaseDiseaseDict;
import com.aeye.cdss.admin.api.vo.dict.BaseDiseaseDictVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [西医]疾病字典(WmkgDiseaseDict)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Service("wmkgDiseaseDictService")
@DS("wmkg")
public class WmkgDiseaseDictServiceImpl extends ServiceImpl<WmkgDiseaseDictMapper, WmkgDiseaseDict> implements WmkgDiseaseDictService {
	@Resource
	private WmkgDiseaseDictMapper wmkgDiseaseDictMapper;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateDiseaseDict(WmkgDiseaseDict entity) {
		this.saveOrUpdate(entity);
	}

	@Override
	public IPage<WmkgDiseaseDictVO> findBaseDiseaseDictPageList(Page<WmkgDiseaseDict> page, BaseDiseaseDictPageDTO dto) {
		Page<WmkgDiseaseDictPO> baseDiseaseDictPageList = wmkgDiseaseDictMapper.findBaseDiseaseDictPageList(page, dto);
		return baseDiseaseDictPageList.convert(item -> BeanConvertUtil.convertBean(item, WmkgDiseaseDictVO.class));
	}

	@Override
	public List<WmkgDiseaseDictVO> findBaseDiseaseDictList(QueryBaseDiseaseDictDTO dto) {
		LambdaQueryWrapper<WmkgDiseaseDict> queryWrapper = Wrappers.<WmkgDiseaseDict>lambdaQuery()
				.in(CollUtil.isNotEmpty(dto.getDiseaseCodeList()), WmkgDiseaseDict::getDiseaseCode, dto.getDiseaseCodeList())
				.like(StringUtils.isNotBlank(dto.getDiseaseName()),WmkgDiseaseDict::getDiseaseName,dto.getDiseaseName())
				.eq(StringUtils.isNotBlank(dto.getNormType()),WmkgDiseaseDict::getNormType,dto.getNormType());

		if (CollUtil.isEmpty(dto.getDiseaseCodeList())
				&& StringUtils.isBlank(dto.getDiseaseName())){
			queryWrapper.last("LIMIT 100");
		}
		queryWrapper.orderByDesc(WmkgDiseaseDict::getCreateTime);
		List<WmkgDiseaseDict> list = this.list(queryWrapper);

		if (StrUtil.isNotBlank(dto.getDiseaseName())){
			list.sort(new Comparator<WmkgDiseaseDict>() {
				@Override
				public int compare(WmkgDiseaseDict p1, WmkgDiseaseDict p2) {
					if (p1.getDiseaseName().equals(dto.getDiseaseName()) && p2.getDiseaseName().equals(dto.getDiseaseName())) {
						return 0;
					} else if (p1.getDiseaseName().equals(dto.getDiseaseName())) {
						return -1; // 完全匹配的元素应该在非完全匹配的元素之前。
					} else if (p2.getDiseaseName().equals(dto.getDiseaseName())) {
						return 1; // 非完全匹配的元素应该在完全匹配的元素之后。
					} else {
						return p1.getDiseaseName().compareTo(p2.getDiseaseName()); // 如果都不匹配，则按照字母顺序排序。
					}
				}
			});
		}

        return BeanConvertUtil.convertListBean(list, WmkgDiseaseDictVO.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeDiseaseDict(List<String> ids) {
		this.removeByIds(ids);
	}
}
