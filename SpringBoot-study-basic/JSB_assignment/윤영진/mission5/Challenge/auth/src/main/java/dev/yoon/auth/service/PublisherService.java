package dev.yoon.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    public void publishMessage(String cookie_value) {

        System.out.println("publish " + cookie_value);
        rabbitTemplate.convertAndSend(
                fanoutExchange.getName(),
                "",
                cookie_value
        );
    }

}
