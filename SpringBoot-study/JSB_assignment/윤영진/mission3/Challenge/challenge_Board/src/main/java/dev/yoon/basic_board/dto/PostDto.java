package dev.yoon.basic_board.dto;

import dev.yoon.basic_board.domain.Post;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {

    private Long userId;

    private Long boardId;

    private String boardName;

    private String title;

    private String content;

    private String writer;

    private String pw;


    public PostDto(Post post) {
        this.userId = post.getUser().getId();
        this.boardId = post.getBoard().getId(); // LAZY
        this.boardName = post.getBoard().getName(); // LAZY
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.pw = "*****";
    }

    public static PostDto createPostDtoPassWordMasked(Post post) {
        PostDto postDto = new PostDto();
        postDto.setUserId(post.getUser().getId());
        postDto.setBoardId(post.getBoard().getId());
        postDto.setBoardName(post.getBoard().getName());
        postDto.setTitle(post.getTitle());
        postDto.setWriter(post.getWriter());
        postDto.setPw("*****");
        postDto.setContent(post.getContent());
        return postDto;
    }


}
