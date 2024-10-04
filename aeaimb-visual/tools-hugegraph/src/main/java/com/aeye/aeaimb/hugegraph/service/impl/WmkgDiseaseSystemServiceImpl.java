package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseSystem;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDiseaseSystemMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseSystemService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 疾病所属系统表(WmkgDiseaseSystem)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:31
 */
@Service("wmkgDiseaseSystemService")
public class WmkgDiseaseSystemServiceImpl extends ServiceImpl<WmkgDiseaseSystemMapper, WmkgDiseaseSystem> implements WmkgDiseaseSystemService {
	@Resource
	private WmkgDiseaseSystemMapper wmkgDiseaseSystemMapper;


}
