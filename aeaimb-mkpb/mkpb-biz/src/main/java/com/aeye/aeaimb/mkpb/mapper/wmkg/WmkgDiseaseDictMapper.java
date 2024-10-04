package com.aeye.aeaimb.mkpb.mapper.wmkg;

import com.aeye.aeaimb.mkpb.entity.cstn.CstnDict;
import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDiseaseDict;
import com.aeye.aeaimb.mkpb.entity.wmkg.po.WmkgDiseaseDictPO;
import com.aeye.aeaimb.mkpb.vo.cstn.CommonVO;
import com.aeye.cdss.admin.api.dto.dict.BaseDiseaseDictPageDTO;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * [西医]疾病字典(WmkgDiseaseDict)表数据库访问层
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
@Mapper
@DS("wmkg")
public interface WmkgDiseaseDictMapper extends BaseMapper<WmkgDiseaseDict> {

	Page<WmkgDiseaseDictPO> findBaseDiseaseDictPageList(Page<WmkgDiseaseDict> page, @Param("o") BaseDiseaseDictPageDTO dto);

	List<CommonVO> getDiseaseDictList(@Param("keyword") String keyword);
}

