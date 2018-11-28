package com.wang.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTool {

	private static Logger logger = LoggerFactory.getLogger(RedisTool.class);
	private static final String REDIS_ADDRESS = "192.168.1.33";
	private static final int REDIS_PORT = 6379;
	private static final String REDIS_AUTH = "";

	private static JedisPool jedisPool = null;

	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;

	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 100;

	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	static
	{
		logger.info("redis开始启动");
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MAX_ACTIVE);
		jedisPoolConfig.setMaxIdle(MAX_IDLE);
		jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
		jedisPoolConfig.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(jedisPoolConfig, REDIS_ADDRESS, REDIS_PORT);
		logger.info("redis启动正常");
	}
	/**
	 * 获取redis连接
	 * @return
	 */
	public static Jedis getJedis(String key)
	{
		return jedisPool.getResource();
	}
	/**
	 * 返还jedis资源
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis)
	{
		jedis.close();
	}

}
