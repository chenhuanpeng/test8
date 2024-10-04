package com.aeye.aeaimb.hugegraph.mapper;

import com.aeye.aeaimb.hugegraph.mapper.entity.BaseDiseaseMapping;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医院疾病映射(BaseDiseaseMapping)表数据库访问层
 *
 * @author linkeke
 * @since 2024-07-21 12:08:40
 */
@Mapper
@DS("base")
public interface BaseDiseaseMappingMapper extends BaseMapper<BaseDiseaseMapping> {


}

