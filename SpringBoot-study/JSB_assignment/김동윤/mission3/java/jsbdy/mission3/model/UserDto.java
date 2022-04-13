package jsbdy.mission3.model;

import jsbdy.mission3.entity.PostEntity;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;
    private String name;
    private List<PostEntity> postentitylist = new ArrayList<>();

    public UserDto(Long id, String name, List<PostEntity> postentitylist) {
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
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postentitylist=" + postentitylist +
                '}';
    }
}
