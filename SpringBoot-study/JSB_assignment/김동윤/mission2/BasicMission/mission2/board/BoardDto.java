package jsbdy.mission2.board;


public class BoardDto {
    private String boardname;

    public BoardDto() {
    }

    public BoardDto(String boardname) {
        this.boardname = boardname;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    @Override
    public String toString() {
        return "BoardDto{" +
                "boardname='" + boardname + '\'' +
                '}';
    }
}