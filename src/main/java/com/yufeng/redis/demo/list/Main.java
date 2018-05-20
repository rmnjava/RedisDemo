package com.yufeng.redis.demo.list;

import com.yufeng.redis.util.list.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * Redis测试代码
 */
public class Main {

    public static void main(String[] args){
        try {
//            Thread producer = new Thread(new Producer());
//            Thread consumer = new Thread(new Consumer());
//
//            producer.start();
//            consumer.start();

//            Thread spyer = new Thread(new ShowRedis());
//            spyer.start();

            //主线程休眠
//            Thread.sleep(Long.MAX_VALUE);

            Jedis conn=RedisUtil.getConnection("192.168.56.101",6379,"");
            System.out.println(RedisUtil.getValue(conn,"runningtask"));
            System.out.println(RedisUtil.getTaskListLength(conn,"runningtask"));
            System.out.println(RedisUtil.getAllMembers(conn,"runningtask",0L,990L));
            RedisUtil.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
