package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgMedicalTermCategory;
import com.aeye.cdss.admin.api.dto.dict.BaseMedicalTermCategoryDictDTO;
import com.aeye.cdss.admin.api.vo.dict.BaseMedicalTermCategoryVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]医疗术语章节分类(WmkgMedicalTermCategory)表服务接口
 *
 * @author linkeke
 * @since 2024-08-24 10:55:17
 */
public interface WmkgMedicalTermCategoryService extends IService<WmkgMedicalTermCategory> {

	List<BaseMedicalTermCategoryVO> findBaseMedicalTermCategoryList(BaseMedicalTermCategoryDictDTO dto);

	List<Object> listNorType();
}
