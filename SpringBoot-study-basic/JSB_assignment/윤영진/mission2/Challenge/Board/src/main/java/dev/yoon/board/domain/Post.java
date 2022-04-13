package dev.yoon.board.domain;

import dev.yoon.board.dto.PostDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<File> files = new ArrayList<>();

    public void addFile(File file) {
        this.files.add(file);
        file.setPost(this);
    }


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
        this.setWriter(postDto.getWriter());
        this.setPw(postDto.getPw());
        this.setContent(postDto.getContent());
        this.setTitle(postDto.getTitle());
    }

}
