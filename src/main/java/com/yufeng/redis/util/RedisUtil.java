package com.yufeng.redis.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Redis数据库分为16个部分[0-15]，默认使用的是0，可以自己指定使用哪个部分
 * 这个类是对redis中的list进行操作的
 *
 * 请大家引以为鉴，redis的连接和关闭是很影响效率的，所以在操作redis时要尽
 * 可能减少连接和关闭的操作，不要和上一个版本代码一样
 *
 * yufeng.fan02@ele.me 2018/1/23
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
     * 获得某个key对应的value
     * @param conn
     * @param key
     * @return
     */
    public static String getValue(Jedis conn,String key){
        String value=conn.get(key);
        return value;
    }

    /**
     * 获得某个key对应的value
     * @param conn
     * @param key
     * @param value
     * @return
     */
    public static String setValue(Jedis conn,String key,String value){
        String result=conn.set(key,value);
        return result;
    }

    /**
     * 获取正在运行的线程的个数
     * @param conn
     * @param key
     * @return
     */
    public static Long getRunningTaskLength(Jedis conn,String key){
        Long length=conn.llen(key);
        return length;
    }

    /**
     * 将新的线程加入正在运行的线程队列
     * @param conn
     * @param key
     * @param threadSpecificId
     * @return
     */
    public static Long rpushRunningTaskList(Jedis conn,String key,String threadSpecificId){
        Long result=conn.rpush(key,threadSpecificId); //result为1表示成功
        return result;
    }

    /**
     * 将已完成的线程排出
     * @param conn
     * @param key
     * @return
     */
    public static String lpopRunningTaskList(Jedis conn,String key){
//        String taskid=jedis.lpop("runningtask");
        String taskid=conn.lpop(key);
        return taskid;
    }

    /**
     * 自动运行的线程队列放在队尾
     * @param conn
     * @param threadSpecificId
     * @param key
     * @return
     */
    public static Long rpushTaskList(Jedis conn,String threadSpecificId,String key){
        Long result=conn.rpush(key,threadSpecificId);
        return result;
    }

    /**
     * 手动运行的线程队列放在队头
     * @param conn
     * @param threadSpecificId
     * @param key
     * @return
     */
    public static Long lpushTaskList(Jedis conn,String threadSpecificId,String key){
        Long result=conn.lpush(key,threadSpecificId);
        return result;
    }

    /**
     * 将新的进程加入正在运行的线程队列
     * @param conn
     * @param key
     * @return
     */
    public static Long popTaskToRunningTaskList(Jedis conn,String key){
        Long result=conn.rpush(key,conn.lpop(key));
        return result;
    }

    /**
     * 获得将要运行的线程队列长度
     * @param conn
     * @param key
     * @return
     */
    public static Long getTaskListLength(Jedis conn,String key){
        Long length=conn.llen(key);
        return length;
    }

    /**
     * 获得lrange返回的结果
     * @param conn
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> getAllMembers(Jedis conn,String key,long start,long end){
        return conn.lrange(key,start,end);
    }

    /**
     * 清空redis中的所有内容，慎用
     * @param conn
     * @return
     */
    public static String flushAllInRedis(Jedis conn){
        String resultString=conn.flushAll();
        return resultString;
    }

    /**
     * 关闭redis和redispool
     */
    public static void close(){
        try{
            jedis.close();
            jedisPool.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
