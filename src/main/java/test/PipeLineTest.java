package test;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import src.JedisPoolUtile;

import java.util.Date;

/**
 * Created by bing on 2016/1/10.
 */
public class PipeLineTest {

    private Jedis master;

    @Before
    public void setUp() {
        this.master = JedisPoolUtile.getJedis();

    }

    @Test
    public void noPipe() {
        Date start = new Date();
        for (int a = 0; a < 100000; a++) {
            master.set("a" + a, "hello world" + a);
        }
        Date end = new Date();
        System.out.println(end.getTime() - start.getTime());
        //Takes 10697
    }

    @Test
    public void pipeLine() {
        Pipeline pipeline = master.pipelined();
        Date start = new Date();
        for (int a = 0; a < 100000; a++) {
            pipeline.set("a" + a, "hello world" + a);
        }
        pipeline.syncAndReturnAll();
        Date end = new Date();
        System.out.println(end.getTime() - start.getTime());
        //Takes 332ms
    }
}
