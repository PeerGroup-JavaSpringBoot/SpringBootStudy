package dev.yoon.auth.repository;

import dev.yoon.auth.model.CookieProcess;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<CookieProcess, String> {}
