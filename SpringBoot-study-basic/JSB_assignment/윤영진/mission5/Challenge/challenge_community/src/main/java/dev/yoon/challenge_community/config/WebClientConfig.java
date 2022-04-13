package dev.yoon.challenge_community.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {


    @Bean
    public WebClient defaultWebClient() {
        /**
         * create(): 가장 기본값의 WebClient
         */
        return WebClient.create();
    }

    /**
     * actuator 서비스에서 사용하기 위한 web Client
     */
    @Bean
    public WebClient ssoClient() {
        /**
         * 해당 Bean에서 반환되는 모든 요청이 http://localhost:10000/current-user 해당 경로에서 시작
         */
        return WebClient.builder()
                .baseUrl("http://localhost:10000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }



}
