import redis.clients.jedis.Jedis;

/**
 * Redis数据库分为16个部分[0-15]，默认使用的是0，可以自己指定使用哪个部分
 * yufeng.fan02@ele.me 2018/1/23
 */
public class CoverageTaskHandler {

    /**
     * 获得某个key对应的value
     * @param key
     * @return
     */
    public static String getValue(String key){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        String value=jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 获得某个key对应的value
     * @param key
     * @return
     */
    public static String setValue(String key,String value){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        String result=jedis.set(key,value);
        jedis.close();
        return result;
    }

    /**
     * 获取正在运行的线程的个数
     * @return
     */
    public static Long getRunningTaskLength(String key){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Long length=jedis.llen(key);
        jedis.close();
        return length;
    }

    /**
     * 将新的线程加入正在运行的线程队列
     * @param threadSpecificId appid:env:prodCommitId:commitId:方式(手工或者自动)
     * @return
     */
    public static Long rpushRunningTaskList(String key,String threadSpecificId){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Long result=jedis.rpush(key,threadSpecificId); //result为1表示成功
        jedis.close();
        return result;
    }

    /**
     * 将已完成的线程排出
     * @return
     */
    public static String lpopRunningTaskList(String key){
        Jedis jedis=new Jedis("127.0.0.1",6379);
//        String taskid=jedis.lpop("runningtask");
        String taskid=jedis.lpop(key);
        jedis.close();
        return taskid;
    }

    /**
     * 自动运行的线程队列放在队尾
     * @param threadSpecificId appid:env:prodCommitId:commitId:方式(手工或者自动)
     * @return
     */
    public static Long rpushTaskList(String threadSpecificId){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Long result=jedis.rpush("tasklist",threadSpecificId);
        jedis.close();
        return result;
    }

    /**
     * 手动运行的线程队列放在队头
     * @param threadSpecificId appid:env:prodCommitId:commitId:方式(手工或者自动)
     * @return
     */
    public static Long lpushTaskList(String threadSpecificId){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Long result=jedis.lpush("tasklist",threadSpecificId);
        jedis.close();
        return result;
    }

    /**
     * 将新的进程加入正在运行的线程队列
     * @return
     */
    public static Long popTaskToRunningTaskList(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Long result=jedis.rpush("runningtask",jedis.lpop("tasklist"));
        jedis.close();
        return result;
    }
    /**
     * 获得将要运行的线程队列长度
     * @return
     */
    public static Long getTaskListLength(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        Long length=jedis.llen("tasklist");
        jedis.close();
        return length;
    }

    /**
     * 清空redis中的所有内容，慎用
     * @return
     */
    public static String flushAllInRedis(){
        Jedis jedis=new Jedis("127.0.0.1",6379);
        String resultString=jedis.flushAll();
        jedis.close();
        return resultString;
    }
}
