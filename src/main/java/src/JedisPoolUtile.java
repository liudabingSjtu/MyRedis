package src; /**
 * Created by bing on 2016/1/10.
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtile {

    private static JedisPool pool;

    /**
     * �������ӳ� ��ʵ������һ������ò���ȱ��ȡ������
     *
     */
    private static void createJedisPool() {

        // �������ӳ����ò���
        JedisPoolConfig config = new JedisPoolConfig();

        // �������������
        config.setMaxTotal(100);


        // �����������ʱ�䣬��ס�Ǻ�����milliseconds
        config.setMaxWaitMillis(1000);

        // ���ÿռ�����
        config.setMaxIdle(10);

        // �������ӳ�
        pool = new JedisPool(config, "192.168.159.130", 6379);

    }

    /**
     * �ڶ��̻߳���ͬ����ʼ��
     */
    private static synchronized void poolInit() {
        if (pool == null)
            createJedisPool();
    }

    /**
     * ��ȡһ��jedis ����
     *
     * @return
     */
    public static Jedis getJedis() {

        if (pool == null)
            poolInit();
        return pool.getResource();
    }

    /**
     * �黹һ������
     *
     * @param jedis
     */
    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }

}