package com.aeye.aeaimb.hugegraph.mapper;

import com.aeye.aeaimb.hugegraph.mapper.entity.BaseServiceMapping;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医院服务项目映射(BaseServiceMapping)表数据库访问层
 *
 * @author linkeke
 * @since 2024-07-22 17:16:05
 */
@Mapper
@DS("base")
public interface BaseServiceMappingMapper extends BaseMapper<BaseServiceMapping> {


}

