package com.aeye.aeaimb.mkpb.controller.wmkg;

import com.aeye.aeaimb.mkpb.service.wmkg.WmkgDrugDictService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 药品字典
 *
 * @author linkeke
 * @since 2024-08-22 08:40:44
 */
@RestController
@RequestMapping("wmkgDrugDict")
public class WmkgDrugDictController {
    /**
     * 服务对象
     */
    @Resource
    private WmkgDrugDictService wmkgDrugDictService;



}

