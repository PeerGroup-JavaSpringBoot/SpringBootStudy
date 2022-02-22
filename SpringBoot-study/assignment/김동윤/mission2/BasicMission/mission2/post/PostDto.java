package jsbdy.mission2.post;

import javax.swing.text.PasswordView;

public class PostDto {
    private String title;
    private String content;
    private  String writer;
    private String password;
    private Integer board_id;

    public PostDto() {
    }

    public PostDto(String title, String content, String writer, String password, Integer board_id) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.board_id = board_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {

        return content;
    }

    public String getWriter() {
        return writer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBoard_id() {
        return board_id;
    }

    public void setBoard_id(Integer board_id) {
        this.board_id = board_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    @Override

    public String toString() {
        return "PostDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", password='" + password + '\'' +
                ", board_id=" + board_id +
                '}';
    }
}