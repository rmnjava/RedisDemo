package com.yufeng.redis.demo.map;

import com.yufeng.redis.util.map.RedisUtil;
import redis.clients.jedis.Jedis;

/**
 * 测试redis对map进行操作
 * @author xianzhixianzhixian 2018.5.20
 */
public class Main {

    public static void main(String[] args){
        Jedis conn=RedisUtil.getConnection("127.0.0.1",6379,"");
        RedisUtil.setKeyFieldValue(conn,"comment","test",10L);
        System.out.println(RedisUtil.getValueFromKeyAndField(conn,"comment","test"));
    }
}
