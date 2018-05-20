package com.yufeng.redis.util.map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis对map进行操作的工具类
 * @author xianzhixianzhixian 2018.5.20
 */
public class RedisUtil {

    private static JedisPoolConfig config=null;
    private static JedisPool jedisPool=null;
    private static Jedis jedis=null;

    /**
     * 获得单例的redis连接
     * @param ip
     * @param port
     * @param passwd
     * @return
     */
    public static Jedis getConnection(String ip,int port,String passwd){

        if(config==null){
            config=new JedisPoolConfig();
            config.setMaxTotal(500);
            config.setMaxIdle(5);
            config.setMaxWaitMillis(1000*10);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
        }
        if(jedisPool==null){
            if(passwd==null || passwd.length()==0) {
                jedisPool = new JedisPool(config, ip, port, 10000);
            }else{
                jedisPool = new JedisPool(config, ip, port, 10000, passwd);
            }
        }
        if (jedis==null){
            jedis=jedisPool.getResource();
        }
        return jedis;
    }

    /**
     * 设置redis map中的key field value
     * @param conn
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long setKeyFieldValue(Jedis conn,String key,String field,Long value){
        return conn.hset(key,field,value.toString());
    }

    /**
     * 根据key field获取value
     * @param conn
     * @param key
     * @param field
     * @return
     */
    public static Long getValueFromKeyAndField(Jedis conn,String key,String field){
        String value=conn.hget(key,field);
        return Long.parseLong(value);
    }
}
