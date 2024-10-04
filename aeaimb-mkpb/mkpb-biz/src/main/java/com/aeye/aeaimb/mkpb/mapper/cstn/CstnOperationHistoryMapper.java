package com.aeye.aeaimb.mkpb.mapper.cstn;


import com.aeye.aeaimb.mkpb.entity.cstn.CstnOperationHistory;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * [问诊]历史记录表 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2024-08-30
 */
@Mapper
@DS("wmkg")
public interface CstnOperationHistoryMapper extends BaseMapper<CstnOperationHistory> {

}
