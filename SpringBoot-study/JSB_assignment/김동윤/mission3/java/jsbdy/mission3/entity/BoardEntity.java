package jsbdy.mission3.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="board")
public class BoardEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="board_name")
    private String name;

    @OneToMany(
            targetEntity = PostEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "boardentity"
            /*PostEntity에 정의된 BoardEntity의 이름*/
    )
    private List<PostEntity> postentitylist = new ArrayList<>();

    public BoardEntity() {
    }

    public BoardEntity(Long id, String name, List<PostEntity> postentitylist) {
        this.id = id;
        this.name = name;
        this.postentitylist = postentitylist;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PostEntity> getPostentitylist() {
        return postentitylist;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostentitylist(List<PostEntity> postentitylist) {
        this.postentitylist = postentitylist;
    }


}

