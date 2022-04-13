package dev.yoon.challenge_community.repository;

import dev.yoon.challenge_community.model.CookieMsg;
import org.springframework.data.repository.CrudRepository;

public interface LogoutRepository extends CrudRepository<CookieMsg, String> {

}
