package com.yufeng.redis.demo.list;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis消费者进程
 * yufeng on 2018/1/22
 */
public class Consumer implements Runnable {

    Jedis jedis = new Jedis("127.0.0.1",6379);
    public void run() {
        while (true){
            try{
                if(jedis.llen("tasklist")==3){
                    for(int i=0;i<jedis.llen("tasklist");i++){
                        System.out.println("lindex i "+jedis.lindex("tasklist",i));
                        if(jedis.llen("map")<3) {
                            System.out.println("rpush map "+jedis.lindex("tasklist",i));
                            jedis.rpush("map",jedis.lindex("tasklist",i));
                        }
                        if(jedis.llen("map")==3){
                            System.out.println("ltrim map "+jedis.ltrim("map",2,0));
                        }
                        Thread.sleep(1000L);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
