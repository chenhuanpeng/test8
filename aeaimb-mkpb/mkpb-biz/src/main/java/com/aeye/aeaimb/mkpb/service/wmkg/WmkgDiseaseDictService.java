package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgDiseaseDict;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgDiseaseDictVO;
import com.aeye.cdss.admin.api.dto.dict.BaseDiseaseDictPageDTO;
import com.aeye.cdss.admin.api.dto.dict.QueryBaseDiseaseDictDTO;
import com.aeye.cdss.admin.api.vo.dict.BaseDiseaseDictVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]疾病字典(WmkgDiseaseDict)表服务接口
 *
 * @author linkeke
 * @since 2024-08-22 08:40:43
 */
public interface WmkgDiseaseDictService extends IService<WmkgDiseaseDict> {

	void saveOrUpdateDiseaseDict(WmkgDiseaseDict entity);

	IPage<WmkgDiseaseDictVO> findBaseDiseaseDictPageList(Page<WmkgDiseaseDict> page, BaseDiseaseDictPageDTO dto);

	List<WmkgDiseaseDictVO> findBaseDiseaseDictList(QueryBaseDiseaseDictDTO dto);

	void removeDiseaseDict(List<String> ids);
}
