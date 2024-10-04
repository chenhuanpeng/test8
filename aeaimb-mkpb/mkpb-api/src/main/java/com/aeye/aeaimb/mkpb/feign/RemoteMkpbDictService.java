package com.aeye.aeaimb.mkpb.feign;

import com.aeye.aeaimb.common.core.constant.ServiceNameConstants;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.sys.SysDictItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lengleng
 * @date 2020/5/12
 * <p>
 * 查询参数相关
 */
@FeignClient(contextId = "remoteMkpbDictService", value = ServiceNameConstants.MKPB_SERVICE)
public interface RemoteMkpbDictService {

	/**
	 * 通过字典类型查找字典
	 * @param type 字典类型
	 * @return 同类型字典
	 */
	@GetMapping("/dict/type/{type}")
	R<List<SysDictItem>> getDictByType(@PathVariable("type") String type);

}
