package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgSynonymsMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgSynonyms;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgSynonymsService;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgSynonymsVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * [西医]同义词(WmkgSynonyms)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 14:08:23
 */
@Service("wmkgSynonymsService")
@DS("wmkg")
public class WmkgSynonymsServiceImpl extends ServiceImpl<WmkgSynonymsMapper, WmkgSynonyms> implements WmkgSynonymsService {

	@Override
	public List<WmkgSynonymsVO> getSynonyms(String keyword, String objType) {
		LambdaQueryWrapper<WmkgSynonyms> queryWrapper =  Wrappers.<WmkgSynonyms>lambdaQuery()
				.eq(StrUtil.isNotBlank(objType), WmkgSynonyms::getObjType, objType)
				.like(StrUtil.isNotBlank(keyword), WmkgSynonyms::getSourceName, keyword);

		List<WmkgSynonyms> list = this.list(queryWrapper);
		return BeanConvertUtil.convertListBean(list, WmkgSynonymsVO.class);
	}

}
