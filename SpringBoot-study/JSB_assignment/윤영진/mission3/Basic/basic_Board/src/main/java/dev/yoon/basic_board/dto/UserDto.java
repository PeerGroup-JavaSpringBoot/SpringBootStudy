package dev.yoon.basic_board.dto;

import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.domain.Post;
import dev.yoon.basic_board.domain.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class UserDto {

    private String name;

    private List<PostDto> postDtos;

    public UserDto(User user) {
        this.name = user.getName();
        this.postDtos = user.getPostList().stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
    }
}
