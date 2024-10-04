package com.aeye.aeaimb.hugegraph.mapper;

import com.aeye.aeaimb.hugegraph.mapper.entity.BaseOperationMapping;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医院手术映射(BaseOperationMapping)表数据库访问层
 *
 * @author linkeke
 * @since 2024-07-21 15:15:37
 */
@Mapper
@DS("base")
public interface BaseOperationMappingMapper extends BaseMapper<BaseOperationMapping> {


}

