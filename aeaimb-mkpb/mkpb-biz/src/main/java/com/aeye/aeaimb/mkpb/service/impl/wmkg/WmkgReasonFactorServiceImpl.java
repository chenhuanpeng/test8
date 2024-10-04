package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonFactorMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonFactor;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonFactorService;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推理因子分类(WmkgReasonFactor)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:24
 */
@Service("wmkgReasonFactorService")
@DS("wmkg")
public class WmkgReasonFactorServiceImpl extends ServiceImpl<WmkgReasonFactorMapper, WmkgReasonFactor> implements WmkgReasonFactorService {

	@Override
	public List<WmkgReasonFactor> getReasonFactorList(String name) {
		return this.list(Wrappers.<WmkgReasonFactor>lambdaQuery()
				.eq(StringUtils.isNotBlank(name),WmkgReasonFactor::getParentFactor, name));
	}
}
