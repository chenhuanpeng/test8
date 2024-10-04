
package com.aeye.aeaimb.mkpb.feign;
import com.aeye.aeaimb.common.core.constant.ServiceNameConstants;
import com.aeye.aeaimb.common.core.constant.SecurityConstants;
import com.aeye.aeaimb.common.core.util.R;
import com.aeye.aeaimb.mkpb.entity.sys.SysOauthClientDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author lengleng
 * @date 2020/12/05
 */
@FeignClient(contextId = "remoteMkpbClientDetailsService", value = ServiceNameConstants.MKPB_SERVICE)
public interface RemoteMkpbClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 * @param clientId 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping("/client/getClientDetailsById/{clientId}")
	R<SysOauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId,
												  @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 查询全部客户端
	 * @param from 调用标识
	 * @return R
	 */
	@GetMapping("/client/list")
	R<List<SysOauthClientDetails>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);

}
