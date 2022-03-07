package dev.yoon.basic_board.dto;

import dev.yoon.basic_board.domain.Board;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDto {

    private Long id;

    @NotEmpty
    private String name;

    private List<PostDto> posts;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.posts = board.getPosts().stream()
                .map(post -> new PostDto(post))
                .collect(Collectors.toList());
    }

//    public static BoardDto createBoardDto(Board board) {
//        BoardDto boardDto = new BoardDto();
//        boardDto.setName(board.getName());
//        for (Post post : board.getPosts()) {
//            boardDto.post_id.add(post.getId());
//        }
//        return boardDto;
//    }



}
