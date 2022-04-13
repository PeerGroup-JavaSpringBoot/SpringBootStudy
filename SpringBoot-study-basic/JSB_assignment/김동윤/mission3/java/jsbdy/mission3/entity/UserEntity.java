package jsbdy.mission3.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
id int
writer varchar
 */
@Entity
@Table(name="user")
public class UserEntity {
    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(
            targetEntity = PostEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "userentity"
    )
    private List<PostEntity> postentitylist = new ArrayList<>();

    public UserEntity(Long id, String name, List<PostEntity> postentitylist) {
        this.id = id;
        this.name = name;
        this.postentitylist = postentitylist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PostEntity> getPostentitylist() {
        return postentitylist;
    }

    public void setPostentitylist(List<PostEntity> postentitylist) {
        this.postentitylist = postentitylist;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postentitylist=" + postentitylist +
                '}';
    }
}
