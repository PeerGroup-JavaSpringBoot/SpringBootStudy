package dev.yoon.basic_community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RefactoringBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefactoringBoardApplication.class, args);
    }

}
