/**
 * created by
 * Date:2019/4/6
 **/
package com.blog.yongyu.demo.Utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;

public class RedisUtils{
    public static int ValidTime = 3600*24*7; //记录有效时间

    private static String addr = "59.77.134.183";
    private static int PORT = 6379;
    private static String auth = "#@.2019&liuyy&";

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    private static int TIMEOUT = 10000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool pool = null;


    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            Integer port = new Integer(PORT);
            pool = new JedisPool(config, addr, port, TIMEOUT, auth);
            try {
                pool.getResource();
            } catch (JedisConnectionException e) {
                System.out.println("redis密码未设置");
                pool = new JedisPool(config, addr, port, TIMEOUT);
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回资源
     * @param pool
     * @param jedis
     */
    public static void returnResource(JedisPool pool, Jedis jedis) {
        if (jedis != null) {
            pool.returnResource(jedis);
        }
    }

    /**
     * 获取数据
     * 释放连接
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
        }finally {
            returnResource(pool,jedis);
        }
        return value;
    }

    /**
     * 存储数据
     * 若存在，会覆盖
     * @param key
     * @param value
     * @return 0 成功； 1失败
     */
    public static String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value);
            return "0";
        } catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
            return "1";
        }finally {
            returnResource(pool,jedis);
        }
    }

    /**
     * 批量删除
     * 删除成功，则返回删除的数量
     * @param keys
     * @return
     */
    public static Long del(String... keys) {
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.del(keys);
        }catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
            return 0L;
        }finally {
            returnResource(pool,jedis);
        }
    }

    /**
     * 通过key值，添加value值
     * @param key
     * @param str
     * @return
     */
    public static Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;
        try{
            jedis = pool.getResource();
            res = jedis.append(key, str);
        }catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
            return 0L;
        }finally {
            returnResource(pool,jedis);
        }
        return res;
    }

    /**
     * 是否存在某个值
     * @param key
     * @return
     */
    public static Boolean exist(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        }catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
            return false;
        }finally {
            returnResource(pool,jedis);
        }
    }

    /**
     * 设置某个值存在时间
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public static String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try{
            jedis = pool.getResource();
            res = jedis.setex(key, seconds, value);
        }catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
        }finally {
            returnResource(pool,jedis);
        }
        return res;
    }

    /**
     * 批量获取
     * @param keys
     * @return
     */
    public static List<String> mget(String... keys) {
        Jedis jedis = null;
        List<String> value = null;
        try {
            jedis = pool.getResource();
            return jedis.mget(keys);
        }catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
        }finally {
            returnResource(pool,jedis);
        }
        return value;
    }

    /**
     * 批量设置
     * 如：obj.mset(new String[] {"key1":"value1";"key2":"value2"}
     * @param keysvalues
     * @return
     */
    public static String mset(String... keysvalues) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = pool.getResource();
            return jedis.mset(keysvalues);
        }catch (Exception e) {
            pool.returnResource(jedis);
            e.printStackTrace();
        }finally {
            returnResource(pool,jedis);
        }
        return res;
    }
}