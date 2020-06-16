package com.tensquare.rabbit;

import com.tensquare.rabbit.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RabbitApplication.class})
public class DirectProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        User user = new User();
        user.setId("1");
        user.setTelephone("19635124458");
        user.setEmail("123456@foxmail.com");
        // 参数1：队列名称，参数2：消息内容
        rabbitTemplate.convertAndSend("test-queue-1", user);
    }

}
