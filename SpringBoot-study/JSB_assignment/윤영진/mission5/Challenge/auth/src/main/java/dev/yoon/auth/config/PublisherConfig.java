package dev.yoon.auth.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobQueue를 선언하기 위해서 하나의 queue에 여러개의 서버가 붙을 수 있도록 queue를 선언
 * Publish Subscribe Message Pattern에서는 Queue를 선언 X
 */
@Configuration
public class PublisherConfig {
    /**
     * FanoutExchange
     * Exchange중 가장 간단한 형태
     * 구독되어 있는 모든 Queue에 메세지를 전달
     * 토픽,헤더 exchange 등등 있음
     * 누구에게는 전달, 누구에게는 전달하지 않을 지를 결정할 수 있는 exchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("boot.fanout.exchange");
    }
}
