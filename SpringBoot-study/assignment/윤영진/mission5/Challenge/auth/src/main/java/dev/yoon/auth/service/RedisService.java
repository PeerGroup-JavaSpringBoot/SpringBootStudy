package dev.yoon.auth.service;

import dev.yoon.auth.model.CookieProcess;
import dev.yoon.auth.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisRepository redisRepository;

    public CookieProcess retrieveJob(String cookieValue) {

        Optional<CookieProcess> cookieProcessOptional = this.redisRepository.findById(cookieValue);

        if (cookieProcessOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return cookieProcessOptional.get();
    }
}
