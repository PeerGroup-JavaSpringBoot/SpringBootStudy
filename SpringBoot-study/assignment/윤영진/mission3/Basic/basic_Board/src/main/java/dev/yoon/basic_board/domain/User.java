package dev.yoon.basic_board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.yoon.basic_board.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    public User(UserDto userDto) {
        this.name = userDto.getName();
    }

    public void update(UserDto userDto) {
        this.name = userDto.getName();
    }

    public void addPost(Post post) {
        postList.add(post);
        post.setUser(this);
    }
}
