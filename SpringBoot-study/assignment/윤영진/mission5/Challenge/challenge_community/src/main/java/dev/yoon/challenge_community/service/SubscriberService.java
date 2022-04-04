package dev.yoon.challenge_community.service;

import dev.yoon.challenge_community.model.CookieMsg;
import dev.yoon.challenge_community.model.CookieProcess;
import dev.yoon.challenge_community.repository.LogoutRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RabbitListener(queues = "#{yoonQueue.name}")
@RequiredArgsConstructor
public class SubscriberService {

    private final LogoutRepository logoutRepository;

    @RabbitHandler
    public void receiveMessage(String cookie_value) {
        log.info("Received: {}", cookie_value);

        CookieMsg cookieMsg = new CookieMsg();
        cookieMsg.setId(cookie_value);
        logoutRepository.save(cookieMsg);

    }


}
