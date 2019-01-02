package com.ys.springboot_test;

import com.ys.springboot_test.config.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTestApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitMqConfig.DIRECT_EXCHANGE, RabbitMqConfig.ROUTINGKEY1,
                "DIRECT发送", correlationId);

        rabbitTemplate.convertAndSend(RabbitMqConfig.TOPPIC_EXCHANGE, "key.topic",
                "TOPPIC发送", correlationId);

        rabbitTemplate.convertAndSend(RabbitMqConfig.FANOUT_EXCHANGE, "",
                "FANOUT发送", correlationId);
    }

}
