package dev.yoon.challenge_community.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JobQueue를 선언하기 위해서 하나의 queue에 여러개의 서버가 붙을 수 있도록 queue를 선언
 * Publish Subscribe Message Pattern에서는 Queue를 선언 X
 */
@Configuration
public class SubscriberConfig {

    /**
     * 이름이 동일해야함
     * 같은 Exchange에서 들어야지 메세지를 받을 수 있음
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("boot.fanout.exchange");
    }

    /**
     * 메세지를 받는 주체이므로 큐가 필요함
     * 익명 큐?
     * 실행 될 때마다 이름을 바꿔서 큐를 생성하는 객체
     */
    @Bean
    public Queue yoonQueue() {
        return new AnonymousQueue();
    }
    /**
     * Queue와 Exchange를 연결
     * Binding: Queue와 Exchange를 연결됨을 알려주는 객체
     * queue와 fanoutExchange는 위의 빈에서 불러옴
     */
    @Bean
    public Binding binding(
            Queue queue,
            FanoutExchange fanoutExchange
    ) {
        /**
         * quque를 fanoutExchange에 연결한다
         */
        return BindingBuilder
                .bind(queue)
                .to(fanoutExchange);
    }

}
