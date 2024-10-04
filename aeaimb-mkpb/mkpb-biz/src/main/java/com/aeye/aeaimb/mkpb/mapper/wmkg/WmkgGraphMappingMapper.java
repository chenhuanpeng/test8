package com.aeye.aeaimb.mkpb.mapper.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgGraphMapping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 图谱标准字典映射 Mapper 接口
 * </p>
 *
 * @author huangzhengri
 * @since 2024-07-29
 */
@Mapper
public interface WmkgGraphMappingMapper extends BaseMapper<WmkgGraphMapping> {
    List<WmkgGraphMapping> selectMappingByCodes(@Param("codes") List<String> codes);
}
