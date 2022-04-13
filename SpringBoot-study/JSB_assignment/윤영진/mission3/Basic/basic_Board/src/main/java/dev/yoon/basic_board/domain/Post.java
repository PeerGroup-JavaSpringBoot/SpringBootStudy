package dev.yoon.basic_board.domain;

import dev.yoon.basic_board.dto.PostDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private String writer;

    private String pw;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 연관관계 편의 메소드
//    public void setBoard(Board board) {
//        this.board = board;
//        board.getPosts().add(this);
//    }

    // 생성 메소드
    public static Post createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setWriter(postDto.getWriter());
        post.setPw(postDto.getPw());
        return post;
    }


    public void update(PostDto postDto) {
        this.setWriter(postDto.getWriter() == null ? writer : postDto.getWriter());
        this.setPw(postDto.getPw() == null ? pw : postDto.getPw());
        this.setContent(postDto.getContent() == null ? content : postDto.getContent());
        this.setTitle(postDto.getTitle() == null ? title : postDto.getTitle());

    }
}
