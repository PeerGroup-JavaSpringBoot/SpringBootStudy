package jsbdy.mission2.board;

import java.util.List;

public interface BoardServicei {
    void createBoard(BoardDto boarddto);
    List<BoardDto> readBoardAll();
    BoardDto readBoard(int id);
    void updateBoard(int id, BoardDto boarddto);
    void deleteBoard(int id);
}
