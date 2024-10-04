package com.aeye.aeaimb.hugegraph.config;

import org.apache.hugegraph.driver.HugeClient;

public class HGFactory {

	// 声明一个静态的Singleton实例，并初始化为null
	private static HugeClient instance = null;

	// 私有构造函数，防止外部类实例化
	private HGFactory() {
	}

	// 提供一个全局的访问点来获取Singleton实例
	public static synchronized HugeClient ins() {
		if (instance == null) {
			instance = init();
		}
		return instance;
	}

	private static HugeClient init() {
		HugeClient hugeClient = HugeClient.builder(HGConfig.HG_URL, HGConfig.HG_NAME).build();
		return hugeClient;
	}

}
