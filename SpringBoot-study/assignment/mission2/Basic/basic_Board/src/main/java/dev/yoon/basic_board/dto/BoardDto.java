package dev.yoon.basic_board.dto;


import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.domain.Post;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDto {

    private String name;

    private List<Long> post_id = new ArrayList<>();


    public static BoardDto createBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setName(board.getName());
        for (Post post : board.getPosts()) {
            boardDto.post_id.add(post.getId());
        }
        return boardDto;
    }

}
