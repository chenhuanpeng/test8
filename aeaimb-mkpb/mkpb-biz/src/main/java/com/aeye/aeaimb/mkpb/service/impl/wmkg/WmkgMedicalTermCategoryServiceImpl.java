package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgMedicalTermCategory;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgMedicalTermCategoryMapper;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgMedicalTermCategoryService;
import com.aeye.cdss.admin.api.dto.dict.BaseMedicalTermCategoryDictDTO;
import com.aeye.cdss.admin.api.entity.BaseMedicalTermCategory;
import com.aeye.cdss.admin.api.vo.dict.BaseMedicalTermCategoryVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * [西医]医疗术语章节分类(WmkgMedicalTermCategory)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-24 10:55:17
 */
@Service("wmkgMedicalTermCategoryService")
@DS("wmkg")
public class WmkgMedicalTermCategoryServiceImpl extends ServiceImpl<WmkgMedicalTermCategoryMapper, WmkgMedicalTermCategory> implements WmkgMedicalTermCategoryService {
    @Resource
    private WmkgMedicalTermCategoryMapper wmkgMedicalTermCategoryMapper;

	@Override
	public List<BaseMedicalTermCategoryVO> findBaseMedicalTermCategoryList(BaseMedicalTermCategoryDictDTO dto) {
		LambdaQueryWrapper<WmkgMedicalTermCategory> queryWrapper = Wrappers.<WmkgMedicalTermCategory>lambdaQuery()
				.like(StringUtils.isNotBlank(dto.getCateName()), WmkgMedicalTermCategory::getCateName, dto.getCateName())
				.eq(StringUtils.isNotBlank(dto.getCateType()), WmkgMedicalTermCategory::getCateType, dto.getCateType())
				.eq(StringUtils.isNotBlank(dto.getCateFor()), WmkgMedicalTermCategory::getCateFor, dto.getCateFor())
				.eq(StringUtils.isNotBlank(dto.getNormType()), WmkgMedicalTermCategory::getNormType, dto.getNormType())
				.eq(StringUtils.isNotBlank(dto.getCateParentCode()), WmkgMedicalTermCategory::getCateParentCode, dto.getCateParentCode());

		List<WmkgMedicalTermCategory> list = this.list(queryWrapper);
		return BeanConvertUtil.convertListBean(list,BaseMedicalTermCategoryVO.class);
	}

	@Override
	public List<Object> listNorType() {
		QueryWrapper<WmkgMedicalTermCategory> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("DISTINCT norm_type");
        return this.getBaseMapper().selectObjs(queryWrapper);
	}
}
