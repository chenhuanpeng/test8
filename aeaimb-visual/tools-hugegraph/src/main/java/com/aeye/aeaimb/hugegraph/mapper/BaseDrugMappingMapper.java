package com.aeye.aeaimb.hugegraph.mapper;

import com.aeye.aeaimb.hugegraph.mapper.entity.BaseDrugMapping;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医院药品映射(BaseDrugMapping)表数据库访问层
 *
 * @author linkeke
 * @since 2024-07-22 18:07:37
 */
@Mapper
@DS("base")
public interface BaseDrugMappingMapper extends BaseMapper<BaseDrugMapping> {


}

