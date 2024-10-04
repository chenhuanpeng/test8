package com.aeye.aeaimb.hugegraph.service.impl;

import com.aeye.aeaimb.hugegraph.mapper.entity.WmkgDiseaseDept;
import com.aeye.aeaimb.hugegraph.mapper.WmkgDiseaseDeptMapper;
import com.aeye.aeaimb.hugegraph.service.WmkgDiseaseDeptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 疾病所属科室(WmkgDiseaseDept)表服务实现类
 *
 * @author linkeke
 * @since 2024-08-27 10:43:30
 */
@Service("wmkgDiseaseDeptService")
public class WmkgDiseaseDeptServiceImpl extends ServiceImpl<WmkgDiseaseDeptMapper, WmkgDiseaseDept> implements WmkgDiseaseDeptService {
	@Resource
	private WmkgDiseaseDeptMapper wmkgDiseaseDeptMapper;


}
