package com.aeye.cdss.daemon.quartz;

import com.aeye.aeaimb.common.feign.annotation.EnableCdssFeignClients;
import com.aeye.aeaimb.common.security.annotation.EnableCdssResourceServer;
import com.aeye.aeaimb.common.swagger.annotation.EnableCdssDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author frwcloud
 * @date 2023-07-05
 */
@EnableCdssDoc(value = "job")
@EnableCdssFeignClients
@EnableCdssResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class CdssQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdssQuartzApplication.class, args);
	}

}
