package com.yufeng.redis.demo.list;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 监控redis中的数据
 */
public class ShowRedis implements Runnable{

    JedisPool jedisPool=new JedisPool("10.103.105.26",6379);
    Jedis jedis=null;
    public void run() {
        while(true){

            try{
                jedis=jedisPool.getResource();
                System.out.println(jedis.lrange("runningtask",0L,99L));
                jedis.close();
                Thread.sleep(5000L);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
