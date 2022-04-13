package dev.yoon.challenge_community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Cookie")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CookieProcess implements Serializable {

    private String id;

    private String name;

}
