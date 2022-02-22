package dev.yoon.board.domain;

import dev.yoon.board.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

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
