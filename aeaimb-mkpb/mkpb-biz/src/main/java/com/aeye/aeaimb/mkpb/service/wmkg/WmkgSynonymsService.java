package com.aeye.aeaimb.mkpb.service.wmkg;

import com.aeye.aeaimb.mkpb.entity.wmkg.WmkgSynonyms;
import com.aeye.aeaimb.mkpb.vo.wmkg.WmkgSynonymsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * [西医]同义词(WmkgSynonyms)表服务接口
 *
 * @author linkeke
 * @since 2024-08-05 14:08:23
 */
public interface WmkgSynonymsService extends IService<WmkgSynonyms> {

	List<WmkgSynonymsVO> getSynonyms(String keyword, String objType);

}
