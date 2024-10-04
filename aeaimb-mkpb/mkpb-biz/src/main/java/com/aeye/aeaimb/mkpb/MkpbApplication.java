
package com.aeye.aeaimb.mkpb;

import com.aeye.aeaimb.common.datasource.annotation.EnableDynamicDataSource;
import com.aeye.aeaimb.common.feign.annotation.EnableCdssFeignClients;
import com.aeye.aeaimb.common.security.annotation.EnableCdssResourceServer;
import com.aeye.aeaimb.common.sso.annotation.EnableSsoFilter;
import com.aeye.aeaimb.common.swagger.annotation.EnableCdssDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lengleng
 * @date 2018年06月21日
 * <p>
 * 用户统一管理系统
 */
@EnableCdssDoc("admin")
@EnableCdssFeignClients
@EnableSsoFilter
@EnableCdssResourceServer
@EnableDiscoveryClient
@EnableDynamicDataSource
@SpringBootApplication
public class MkpbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkpbApplication.class, args);
	}

}
