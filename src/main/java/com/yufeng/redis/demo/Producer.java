package com.yufeng.redis.demo;

import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * Redis生产者进程
 * yufeng on 2018/1/22
 */
public class Producer implements Runnable{

    public static int i=0;
    Jedis jedis = new Jedis("10.101.92.50",6379);
    public void run() {
        while (true){
            try{
                if(jedis.llen("tasklist")<3){
                    jedis.rpush("tasklist",i+"");
                    System.out.println("rpush tasklist "+i);
                    i++;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Jedis jedis = new Jedis("10.101.92.50",6379);
//        jedis.lpush("tasklist","1");
//        jedis.lpush("tasklist","2");
//        jedis.lpush("tasklist","3");
//        jedis.lpush("tasklist","4");
//        jedis.lpush("tasklist","5");
//        jedis.lpush("tasklist","6");
        System.out.println(jedis.llen("tasklist"));
        System.out.println(jedis.lindex("tasklist",3L));
    }
}
