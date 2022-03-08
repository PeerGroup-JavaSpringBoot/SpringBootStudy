package dev.yoon.basic_board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yoon.basic_board.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "BOARD")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> posts;


    public void addPost(Post post) {
        this.posts.add(post);
        post.setBoard(this);
    }

    public static Board createBoard(BoardDto boardDto) {
        Board board = new Board();
        board.setName(boardDto.getName());
        return board;
    }


}
