package dev.yoon.basic_community.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DateTime {

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
