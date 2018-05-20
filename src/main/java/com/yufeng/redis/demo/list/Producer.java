package com.yufeng.redis.demo.list;

import redis.clients.jedis.Jedis;

/**
 * Redis生产者进程
 * yufeng on 2018/1/22
 * 优先级高的进程LPUSH-在栈顶，优先级低的进程RPUSH-在栈底；POP采用LPOP，每次只取栈顶元素，
 * 再创建一个map栈满三个的时候LTRIM清空即可
 */
public class Producer implements Runnable{

    public static int i=0;
    Jedis jedis = new Jedis("127.0.0.1",6379);
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
        Jedis jedis = new Jedis("127.0.0.1",6379);
//        jedis.lpush("tasklist","1");
//        jedis.lpush("tasklist","2");
//        jedis.lpush("tasklist","3");
//        jedis.lpush("tasklist","4");
//        jedis.lpush("tasklist","5");
//        jedis.lpush("tasklist","6");
//        System.out.println(jedis.llen("tasklist"));
//        System.out.println(jedis.lindex("tasklist",3L));
        System.out.println(jedis.lpush("runningtask","0"));
        System.out.println(jedis.lpop("runningtask"));
    }
}
