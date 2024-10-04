package com.aeye.aeaimb.mkpb.mapper.wmkg;

import com.aeye.aeaimb.mkpb.dto.cdn.ReasonDefinePageDTO;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgReasonDefine;
import com.aeye.aeaimb.mkpb.vo.cdn.ReasonDefineCountVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 推理疾病定义(WmkgReasonDefine)表数据库访问层
 *
 * @author linkeke
 * @since 2024-08-05 13:41:23
 */
@Mapper
public interface WmkgReasonDefineMapper extends BaseMapper<WmkgReasonDefine> {

	IPage<ReasonDefineCountVO> getReasonDefineCountPageList(Page<WmkgReasonDefine> page, @Param("o") ReasonDefinePageDTO dto);
}

