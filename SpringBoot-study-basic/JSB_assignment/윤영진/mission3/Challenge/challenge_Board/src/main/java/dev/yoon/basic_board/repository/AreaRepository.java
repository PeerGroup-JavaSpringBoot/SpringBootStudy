package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area,Long> {
}
