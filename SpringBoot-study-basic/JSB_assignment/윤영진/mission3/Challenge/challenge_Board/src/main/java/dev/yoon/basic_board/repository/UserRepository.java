package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u join fetch u.area")
    List<User> findAll();

    @Query("select u from User u join fetch u.area "+
            " where u.id =:userId")
    Optional<User> findById(Long userId);
}
