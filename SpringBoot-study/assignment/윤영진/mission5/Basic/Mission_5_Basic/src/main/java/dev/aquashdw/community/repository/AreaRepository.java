package dev.aquashdw.community.repository;

import dev.aquashdw.community.entity.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

public interface AreaRepository extends JpaRepository<AreaEntity, Long> {

}
