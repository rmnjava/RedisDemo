package com.yufeng.redis.demo;

import redis.clients.jedis.Jedis;

/**
 * 监控redis中的数据
 */
public class ShowRedis implements Runnable{

    Jedis jedis=new Jedis("10.103.105.26",6379);
    public void run() {
        while(true){

            try{
                System.out.println(jedis.lrange("runningtask",0L,99L));
                Thread.sleep(5000L);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
