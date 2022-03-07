package dev.yoon.basic_board.repository;

import dev.yoon.basic_board.domain.Board;
import dev.yoon.basic_board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class BoardRepositoryImpl implements BoardRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Board board) {
        em.persist(board);
    }

    @Override
    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    @Override
    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    @Override
    public List<Board> findByTitle(String name) {
        return em.createQuery("select b from Board b where b.name= :name", Board.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public boolean updateBoard(Long id, BoardDto dto) {

        Board board = em.find(Board.class, id);

        if(board == null) {
            return false;
        }
        board.setName(dto.getName());
        return true;
    }

    @Override
    public void delete(Board board) {
        em.remove(board);
    }
}
