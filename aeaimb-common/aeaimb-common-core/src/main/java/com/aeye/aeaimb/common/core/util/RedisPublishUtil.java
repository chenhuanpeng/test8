package com.aeye.aeaimb.common.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.io.Serializable;


/**
 * Redis 订阅消息发布组件
 * @author yangjing
 * @date 2024/8/22
 */
@Slf4j
public class RedisPublishUtil {
	public RedisPublishUtil(RedisTemplate<String, Object> redisTemplate){
		this.redisTemplate = redisTemplate;
	}

	private RedisTemplate<String, Object> redisTemplate;
	/**
	 * 向频道发布消息
	 * @param channel   频道
	 * @param message   消息
	 * @return true成功 false失败
	 */
	public boolean publish(String channel, Object message) {
		if (!StringUtils.hasText(channel)) {
			return false;
		}
		try {
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.convertAndSend(channel, message);
			log.info("发送消息成功，channel:{}, message:{}", channel, message);
			return true;
		} catch (Exception e) {
			log.error("发送消息失败，channel:{}, message:{}", channel, message, e);
		}
		return false;
	}
}
