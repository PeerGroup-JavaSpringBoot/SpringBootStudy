package eci.server.NewItemModule.repository.classification;

import eci.server.NewItemModule.entity.classification.Classification1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Classification1Repository extends JpaRepository<Classification1, Long> {
        @Query("select c from Classification1 c join fetch c.classification2List ")
        List<Classification1> findAllByClassification1();

        }
