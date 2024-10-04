package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgSymptomDict;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgSymptomDictVO;
import com.aeye.cdss.admin.api.dto.dict.BaseSymptomDictPageDTO;
import com.aeye.cdss.admin.api.dto.dict.BaseSymptomDictQueryDTO;
import com.aeye.cdss.admin.api.entity.BaseSymptomDict;
import com.aeye.cdss.admin.api.vo.dict.BaseSymptomDictVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]症状字典(WmkgSymptomDict)表服务接口
 *
 * @author linkeke
 * @since 2024-08-24 13:38:21
 */
public interface WmkgSymptomDictService extends IService<WmkgSymptomDict> {

	void saveOrUpdateSymptomDict(WmkgSymptomDict entity, String addFlag);

	void removeSymptomDict(List<String> ids);
	IPage<WmkgSymptomDictVO> findBaseSymptomDictPageList(Page<WmkgSymptomDict> page, BaseSymptomDictPageDTO dto);
	List<WmkgSymptomDictVO> findBaseSymptomDictList(BaseSymptomDictQueryDTO dto);
}
