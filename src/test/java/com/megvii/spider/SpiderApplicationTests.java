package com.sy.spider;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import com.alibaba.fastjson.JSONObject;
import com.sy.spider.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.sy.spider.mapper")
public class SpiderApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisTest1() {
        for (int i = 0; i < 100000; i++) {
            redisTemplate.opsForValue().set("key-" + i, "value-" + i);
        }
    }

    @Test
    public void redisTest2() {

        List<Object> lists = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    redisConnection.set(("key-" + i).getBytes(), ("value-" + i).getBytes());
                }
                //这里必须是返回null
                return null;
            }
        });

    }

    @Test
    public void redisTest3() {
        User u = new User();
        u.setId(1);
        u.setUsername("peter");
        u.setPassword("123456");
        u.setPhone("11111111");
        List<User> lists = redisTemplate.executePipelined(new RedisCallback<User>() {

            @Override
            public User doInRedis(RedisConnection redisConnection) throws DataAccessException {
                //10秒就会过期
                redisConnection.setEx("user1".getBytes(), 10,JSONObject.toJSONBytes(u));
                return null;
            }
        });

        System.out.println(lists.size());
        System.out.println(lists.get(0));
    }


    @Test
    public void redisTest4() {

        List<Long> lists = redisTemplate.executePipelined(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 100000; i++) {
                    redisConnection.zCount(("key-" + i).getBytes(), 0, Integer.MAX_VALUE);
                }
                //这里必须是返回null
                return null;
            }
        });

        System.out.println(lists.size());
        System.out.println(lists.get(0));
        System.out.println(lists.get(1));
    }

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Test
    public void rabbitMqTest(){
        String context = "hello peter :" + new Date();
        this.rabbitTemplate.convertAndSend("000001", context);
    }

    public static void main(String[] args) {
        Writer w = null;
        try {
            w = new FileWriter("d:/data/redis-data-init.txt");
            for (int i = 0; i < 100000; i++) {

                w.write("set key-" + i + " value-" + i +" \r\n");
            }
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
