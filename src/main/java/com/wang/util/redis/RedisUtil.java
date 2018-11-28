package com.wang.util.redis;


import com.wang.util.Pager;
import com.wang.util.StringUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author chenpang
 *
 */
@Component("redisUtil")
public class RedisUtil {
	private static final Charset UTF_8 = Charset.forName("utf-8");
	
	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	/*************     STRING 操作       **************/
	
	public boolean addString(String key, String value) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		logger.info("redis入库string, key:" + key + ",value:" + value);
		Jedis jedis = RedisTool.getJedis(key);
		String result = jedis.set(key, value);
		RedisTool.returnJedis(jedis);
		return "OK".equals(result);
	}

	public boolean addStringExipre(String key, String value, int second) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		logger.info("redis入库string, key:" + key + ",value:" + value +",超时时间：" + second);
		Jedis jedis = RedisTool.getJedis(key);
		String result = jedis.setex(key, second, value);
		RedisTool.returnJedis(jedis);
		return "OK".equals(result);
	}

	public String getString(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		String result = jedis.get(key);
		RedisTool.returnJedis(jedis);
		return result;
	}

	public Long increaseString(String key, int count) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.incrBy(key, count);
		RedisTool.returnJedis(jedis);
		return result;
	}

	/*****************   KEY操作  **************/
	public boolean delKey(String key) {
		if (StringUtils.isBlank(key)) {
			return true;
		}
		logger.info("redis删除key, key:" + key );
		Jedis jedis = RedisTool.getJedis(key);
		jedis.del(key);
		RedisTool.returnJedis(jedis);
		return true;
	}

	public boolean expire(String key, int second) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		logger.info("redis设置超时时间, key:" + key + ",second:" + second);
		Jedis jedis = RedisTool.getJedis(key);
		jedis.expire(key, second);
		RedisTool.returnJedis(jedis);
		return true;
	}
	public Set<String> keys(String pattern) {
		if (StringUtils.isBlank(pattern)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(pattern);
		Set<String> set = jedis.keys(pattern);
		RedisTool.returnJedis(jedis);
		return set;
	}

	public boolean exists(String key) {
		if (StringUtils.isBlank(key)) {
			return true;
		}
		Jedis jedis = RedisTool.getJedis(key);
		boolean result = jedis.exists(key);
		RedisTool.returnJedis(jedis);
		return result;
	}

	public boolean watch(String key){
		if (StringUtils.isBlank(key)) {
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		String result = jedis.watch(key);
		RedisTool.returnJedis(jedis);
		return "OK".equals(result);
	}

	/**
	 * 当 key 不存在时，返回 -2 。
	 * 当 key 存在但没有设置剩余生存时间时，返回 -1 。
	 * 否则，以秒为单位，返回 key 的剩余生存时间。
	 * @param key
	 * @return
	 */
	public Long ttlKey(String key){
		if (StringUtils.isBlank(key)) {
			return -1L;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.ttl(key);
		RedisTool.returnJedis(jedis);
		return result;
	}

	/******************    MAP 操作     ********************/
	
	public void addMap(String key, Map<String, String> map) {
		if (StringUtils.isBlank(key) || map == null) {
			return;
		}
		logger.info("redis入库map, key:" + key + ",value:" + map);
		Jedis jedis = RedisTool.getJedis(key);
		jedis.hmset(key, map);
		RedisTool.returnJedis(jedis);
	}

	public boolean addMapVal(String key, String field, String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(key)
				|| StringUtils.isBlank(key)) {
			return false;
		}
		logger.info("redis入库map单项值, key:" + key + ",field:" + field + ",value:" + value);
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.hsetnx(key, field, value);
		RedisTool.returnJedis(jedis);
		return result.intValue() ==1;
	}
	
	public boolean updateMapVal(String key, String field, String value){
		if (StringUtils.isBlank(key) || StringUtils.isBlank(key)
				|| StringUtils.isBlank(key)) {
			return false;
		}
		logger.info("redis更库map, key:" + key + ",field:" + field + ",value:" + value);
		Jedis jedis = RedisTool.getJedis(key);
		jedis.hset(key, field, value);
		RedisTool.returnJedis(jedis);
		return true;
	}

	public boolean increaseMapVal(String key, String field, Long value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(key)
				|| StringUtils.isBlank(key)) {
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.hincrBy(key, field, value);
		RedisTool.returnJedis(jedis);
		return true;
	}


	public Map<String, String> getMap(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Map<String, String> map = jedis.hgetAll(key);
		RedisTool.returnJedis(jedis);
		return map;
	}
	
	public String getMapField(String key, String field)
	{
		if(StringUtils.isBlank(key) || StringUtils.isBlank(field)) {return null;}
		Jedis jedis = RedisTool.getJedis(key);
		String result = jedis.hget(key, field);
		RedisTool.returnJedis(jedis);
		return result;
	}
	
	public List<String> getMapFields(String key, String... fields){
		if(StringUtils.isBlank(key) || fields == null) {return null;}
		Jedis jedis = RedisTool.getJedis(key);
		List<String> result = jedis.hmget(key, fields);
		RedisTool.returnJedis(jedis);
		return result;
	}
	
	public Map<String, Map<String, String>> fetchGetMap(RedisKeyType keyType, String ...key)
	{
		if(key == null || keyType == null) {return null;}
		logger.info("redis管道入库map, key:" + key + ",keyType:" + keyType);
		Map<String, Response<Map<String, String>>> map = Maps.newHashMap();
		Map<String, Map<String, String>> result = Maps.newHashMap();
		
		Jedis jedis = RedisTool.getJedis(keyType.getText());
		//管道批量操作
		Pipeline pipeline = jedis.pipelined();
		for (String string : key) {
			map.put(string, pipeline.hgetAll(RedisKeyUtil.getKeyByType(string, keyType)));
		}
		pipeline.sync();
		for (Entry<String, Response<Map<String, String>>> entry : map.entrySet()) {
			result.put(entry.getKey(), entry.getValue().get());
		}
		RedisTool.returnJedis(jedis);
		return result;
	}
	
	/***************     LIST 操作   ******************/

	public void addList(String key, String... values) {
		if (StringUtils.isBlank(key) || values == null) {
			return;
		}
		logger.info("redis入库list, key:" + key + ",values:" + values);
		Jedis jedis = RedisTool.getJedis(key);
		jedis.rpush(key, values);
		RedisTool.returnJedis(jedis);
	}

	public List<String> getListVals(String key, int start, int end) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		List<String> rtnList = jedis.lrange(key, start, end);
		RedisTool.returnJedis(jedis);
		return rtnList;
	}
	
	/*******************    SET 操作     *********************/
	
	public void addSet(String key, String... values) {
		if (StringUtils.isBlank(key) || values == null) {
			return;
		}
		logger.info("redis入库set, key:" + key + ",values:" + values);
		Jedis jedis = RedisTool.getJedis(key);
		jedis.sadd(key, values);
		RedisTool.returnJedis(jedis);
	}

	public void delSetVal(String key, String... fields) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		logger.info("redis删除set, key:" + key + ",values:" + fields);
		Jedis jedis = RedisTool.getJedis(key);
		jedis.srem(key, fields);
		RedisTool.returnJedis(jedis);
	}

	public Set<String> getSetVals(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Set<String> rtnSet = jedis.smembers(key);
		RedisTool.returnJedis(jedis);
		return rtnSet;
	}

	public String getSetRandomVal(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		String result = jedis.srandmember(key);
		RedisTool.returnJedis(jedis);
		return result;
	}

	public boolean isSetContain(String key, String field) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		boolean isContain = jedis.sismember(key, field);
		RedisTool.returnJedis(jedis);
		return isContain;
	}

	public Long getSetLength(String key) {
		if (null == key) {
			return 0L;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long length = jedis.scard(key);
		RedisTool.returnJedis(jedis);
		return length;
	}
	
	/********************* SORT SET 操作    *************************/
	public static boolean addSortSet(String key, String member){
		return addSortSet(key, System.currentTimeMillis(), member);
	}
	public static boolean addSortSet(String key, double score, String member)
	{
		if(StringUtils.isBlank(key) || StringUtils.isBlank(member)){
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.zadd(key, score, member);
		RedisTool.returnJedis(jedis);
		if(result == null) {return false;}
		return result.intValue() > 0;
	}
	
	public boolean delSortSet(String key, String... member){
		if(StringUtils.isBlank(key) || member == null){
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.zrem(key, member);
		RedisTool.returnJedis(jedis);
		if(result == null) return false;
		return result.intValue() > 0;
	}

	public boolean delSortSetByScore(String key, Double score){
		if(StringUtils.isBlank(key) || score == null){
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.zremrangeByScore(key, score, score);
		RedisTool.returnJedis(jedis);
		if(result == null) return false;
		return result.intValue() > 0;
	}

	public boolean delSortSetAll(String key) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.zremrangeByRank(key, 0, -1);
		RedisTool.returnJedis(jedis);
		if(result == null) return false;
		return result.intValue() > 0;
	}
	
	public boolean isSortSetContain(String key, String member)
	{
		if(StringUtils.isBlank(key) || StringUtils.isBlank(member)) {return false;}
		Jedis jedis = RedisTool.getJedis(key);
		Long result = jedis.zrank(key, member);
		RedisTool.returnJedis(jedis);
		if(result == null) {return false;}
		return result.intValue() >= 0;
	}
	
	public List<String> getSortSetValAsc(String key, int start, int end)
	{
		if(StringUtils.isBlank(key)) {return null;}
		Jedis jedis = RedisTool.getJedis(key);
		Set<String> sets = jedis.zrange(key, start, end);
		RedisTool.returnJedis(jedis);
		if(sets == null || sets.size() <= 0) {return null;}
		
		return new ArrayList<String>(sets);
	}
	public List<String> getSortSetValDesc(String key, int start, int end)
	{
		if(StringUtils.isBlank(key)) {return null;}
		Jedis jedis = RedisTool.getJedis(key);
		Set<String> sets = jedis.zrevrange(key, start, end);
		RedisTool.returnJedis(jedis);
		if(sets == null || sets.size() <= 0) {return null;}

		return new ArrayList<String>(sets);
	}

	/**
	 * 取两个SortSet的交集，并保存为另一个Key
	 * @param oneKey
	 * @param twoKey
	 * @return
	 */
	public List<String> getSortSetSinter(String oneKey,String twoKey)
	{
		if(StringUtils.isBlank(oneKey)) {return null;}
		Jedis jedis = RedisTool.getJedis(oneKey);
		Long count = jedis.zinterstore("jiaoji",oneKey,twoKey);
		System.out.println("---------------------------------------存入了----------------"+count);
		Set<String> sets = jedis.zrevrange("jiaoji", 0, -1);
		RedisTool.returnJedis(jedis);
		if(sets == null || sets.size() <= 0) {return null;}

		return new ArrayList<String>(sets);
	}
	/**
	 *  根据score区间,获取分页String集合
	 * @param key
	 * @param beginScore
	 * @param endScore
	 * @return
	 */
	public Long getSortSetCountByScore(String key, double beginScore,double endScore) {

		if(StringUtils.isBlank(key))
		{
			return 0L;
		}
		Jedis jedis = RedisTool.getJedis(key);
		Long count=jedis.zcount(key, beginScore, endScore);
		RedisTool.returnJedis(jedis);
		 return	count==null?0L:count;
	}


	/**
	 *  根据score区间,获取分页String集合
	 * @param key
	 * @param beginScore
	 * @param endScore
	 * @param pager pager为null则不分页
	 * @return
	 */
	public List<String> getSortSetValByScore(String key, Double beginScore,Double endScore,Pager pager)
	{
		Set<String> sets;
		if(StringUtils.isBlank(key))
		{
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		if(pager==null)
		{
			 sets = jedis.zrangeByScore(key, beginScore, endScore)  ;
		} else {
			sets = jedis.zrangeByScore(key, beginScore, endScore, (pager.getPageNumber() - 1) * pager.getLimit(), pager.getLimit());
		}
		RedisTool.returnJedis(jedis);
		if(sets == null || sets.size() <= 0)
		{
			return null;
		}

		return new ArrayList<String>(sets);
	}
	/**
	 *  根据score区间,获取分页String倒序集合
	 * @param key
	 * @param beginScore
	 * @param endScore
	 * @param pager pager为null则不分页
	 * @return
	 */
	public List<String> getSortSetValByScoreDesc(String key, Double beginScore,Double endScore,Pager pager)
	{
		Set<String> sets;
		if(StringUtils.isBlank(key))
		{
			return null;
		}
		Jedis jedis = RedisTool.getJedis(key);
		if(pager==null)
		{
			sets = jedis.zrevrangeByScore(key, endScore, beginScore)  ;
		}
		else
		{
			sets =jedis.zrevrangeByScore(key,  endScore, beginScore,(pager.getPageNumber() - 1) * pager.getLimit(), pager.getLimit());
		}
		RedisTool.returnJedis(jedis);
		if(sets == null || sets.size() <= 0)
		{
			return null;
		}

		return new ArrayList<String>(sets);
	}

	public static Long getSortSetLength(String key)
	{
		if(StringUtils.isBlank(key)) return 0l;
		Jedis jedis = RedisTool.getJedis(key);
//		Long result = jedis.zcount(key, "-inf", "+inf");
		Long result = jedis.zcard(key);
		RedisTool.returnJedis(jedis);
		return result==null?0L:result;
	}

	public boolean increaseSortSetScore(String key, double score, String member) {
		if (StringUtils.isBlank(key)) {
			return false;
		}
		Jedis jedis = RedisTool.getJedis(key);
		jedis.zincrby(key, score, member);
		RedisTool.returnJedis(jedis);
		return true;
	}
	
	
	/********************* JSON 操作  弃用 ************************/
	public boolean addObejct(String key, Object obj)
	{
		if(StringUtils.isBlank(key) || obj == null){
			return false;
		}
		logger.info("redis入库object, key:" + key + ",Object:" + obj);
//		String objString = gson.toJson(obj);
		Map map = convertMap(obj);
		Jedis jedis = RedisTool.getJedis(key);
//		String result = jedis.set(key, objString);
		String result = jedis.hmset(key, map);
		RedisTool.returnJedis(jedis);
		return "OK".equals(result);
	}
	
	private Map convertMap(Object obj)
	{
		if(obj == null) {return null;}
		Map map = Maps.newHashMap();
		BeanMap beanMap = BeanMap.create(obj);
		for(Object key : beanMap.keySet()){
			Object value = beanMap.get(key);
			if(value == null) {continue;}
			map.put(key + "", value.toString());
		}
		return map;
	}
	
	
	public <T> T getObject(String key, Class<T> clazz){
		if(StringUtils.isBlank(key) || clazz == null) {return null;}
		logger.info("redis获取object, key:" + key + ",clazz:" + clazz);
		Jedis jedis = RedisTool.getJedis(key);
//		String result = jedis.get(key);
		Map<String, String> map = jedis.hgetAll(key);
		RedisTool.returnJedis(jedis);
		
		if(map == null) {return null;}
//		T obj = gson.fromJson(result, clazz);
		try {
			T t = clazz.newInstance();
			BeanMap beanMap = BeanMap.create(t);
			beanMap.putAll(beanMap);
			return t;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/******************      序列化操作      *************************/
	/**
	 * 禁止返回HIBERNATE对象
	 * @param key
	 * @param tClass
	 * @return
	 */
	public <T>T  getObjectByKey(String key, Class<T> tClass)
    {
		Jedis jedis = RedisTool.getJedis(key);
        byte[] byt=jedis.get(key.getBytes());
        Object obj= unserizlize(byt);
        RedisTool.returnJedis(jedis);
        if(obj!=null && obj.getClass().getName()==tClass.getName()){
            return (T)obj;
        }
        return null;
    }
	public void saveObject(String key,Object Obj)
    {
		Jedis jedis = RedisTool.getJedis(key);
        jedis.set(key.getBytes(), serialize(Obj));
        RedisTool.returnJedis(jedis);
    }


    //序列化
    private static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    private static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {

            e.printStackTrace();
        }


        return null;
    }

}
