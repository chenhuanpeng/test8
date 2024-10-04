package com.aeye.aeaimb.mkpb.service.impl.wmkg;

import cn.hutool.core.util.StrUtil;
import com.aeye.aeaimb.common.core.util.BeanConvertUtil;
import com.aeye.aeaimb.common.core.util.RedisPublishUtil;
import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefineDTO;
import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefinePageDTO;
import com.aeye.aeaimb.mkpb.mapper.wmkg.WmkgReasonDefineMapper;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDefine;
import com.aeye.aeaimb.mkpb.service.wmkg.WmkgReasonDefineService;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineCountVO;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineVO;
import com.aeye.aeaimb.wmkg.mq.ChannelConstants;
import com.aeye.aeaimb.wmkg.mq.ReasonReleaseDto;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 推理疾病定义(WmkgReasonDefine)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Service("wmkgReasonDefineService")
@DS("wmkg")
public class WmkgReasonDefineServiceImpl extends ServiceImpl<WmkgReasonDefineMapper, WmkgReasonDefine> implements WmkgReasonDefineService {

	@Resource
	WmkgReasonDefineMapper wmkgReasonDefineMapper;
//	@Resource
//	DiseaseRulesLoader diseaseRulesLoader;

	@Resource
	RedisPublishUtil redisPublishUtil;

	@Override
	public IPage<ReasonDefineCountVO> getReasonDefineCountPageList(Page<WmkgReasonDefine> page, ReasonDefinePageDTO dto) {
		return wmkgReasonDefineMapper.getReasonDefineCountPageList(page, dto);
	}

	@Override
	public IPage<ReasonDefineVO> getReasonDefinePageList(Page<WmkgReasonDefine> page, ReasonDefinePageDTO dto) {
		LambdaQueryWrapper<WmkgReasonDefine> queryWrapper = Wrappers.<WmkgReasonDefine>lambdaQuery()
				.eq(WmkgReasonDefine::getDiseaseCode, dto.getDiseaseCode())
				.like(StringUtils.isNotBlank(dto.getReasonName()), WmkgReasonDefine::getReasonName, dto.getReasonName());
		if (StrUtil.isNotBlank(dto.getReasonStatus())){
			queryWrapper.eq(WmkgReasonDefine::getReasonStatus, dto.getReasonStatus());
		}
		Page<WmkgReasonDefine> page1 = this.page(page, queryWrapper);
		return page1.convert(item -> BeanConvertUtil.convertBean(item, ReasonDefineVO.class));
	}

	@Override
	public void setReasonDefineStatus(ReasonDefineDTO dto) {
		LambdaUpdateWrapper<WmkgReasonDefine> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.eq(WmkgReasonDefine::getReasonId, dto.getReasonId());
		if (Objects.nonNull(dto.getReasonStatus())){
			updateWrapper.set(WmkgReasonDefine::getReasonStatus, dto.getReasonStatus());
			if (Objects.equals(dto.getReasonStatus(), 1)){
				redisPublishUtil.publish(ChannelConstants.REASON_RELEASE_CHANNEL, new ReasonReleaseDto(dto.getReasonId(), dto.getReasonName(), ChannelConstants.ReleaseType.RELEASE.name()));
//				diseaseRulesLoader.addPredictCal(this.getById(dto.getReasonId()));
			}else{
//				diseaseRulesLoader.removePredictCal(dto.getReasonId());
				redisPublishUtil.publish(ChannelConstants.REASON_RELEASE_CHANNEL, new ReasonReleaseDto(dto.getReasonId(), dto.getReasonName(), ChannelConstants.ReleaseType.CANCEL.name()));
			}
		}
		if (StrUtil.isNotBlank(dto.getDelFlag())){
			updateWrapper.set(WmkgReasonDefine::getDelFlag, dto.getDelFlag());
//			diseaseRulesLoader.removePredictCal(dto.getReasonId());
		}
		this.update(updateWrapper);
	}
}
