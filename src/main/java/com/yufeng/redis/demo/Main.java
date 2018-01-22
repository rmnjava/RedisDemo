package com.yufeng.redis.demo;

/**
 * Redis测试代码
 */
public class Main {

    public static void main(String[] args){
        try {
            Thread producer = new Thread(new Producer());
            Thread consumer = new Thread(new Consumer());

            producer.start();
            consumer.start();

            //主线程休眠
            Thread.sleep(Long.MAX_VALUE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
