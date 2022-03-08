package jsbdy.mission3.model;

public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private String password;
    private Long boardId;
    private Long userId;


    public PostDto(){

    }
    public PostDto(Long id, String title, String content, String writer, String password, Long boardId, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.boardId = boardId;
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PostDto passwordMasked(){
        return new PostDto(
                this.id,
                this.title,
                this.content,
                this.writer,
                "*****",
                this.boardId,
                this.userId
        );
    }
}
