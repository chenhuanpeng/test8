package com.aeye.aeaimb.mkpb.mapper.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgSymptomDict;
import com.aeye.aeaimb.mkpb.vo.cstn.CommonVO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [西医]症状字典(WmkgSymptomDict)表数据库访问层
 *
 * @author linkeke
 * @since 2024-08-24 13:38:21
 */
@Mapper
@DS("wmkg")
public interface WmkgSymptomDictMapper extends BaseMapper<WmkgSymptomDict> {

	List<CommonVO> getSymptomDictList(@Param("keyword") String keyword);

}

