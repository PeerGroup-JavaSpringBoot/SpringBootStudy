package dev.yoon.board.repository;

import dev.yoon.board.domain.File;
import dev.yoon.board.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public List<Post> findAll() {
        return em.createQuery("select b from Post b", Post.class).getResultList();
    }

    @Override
    public Post findById(Long id) {
        return em.find(Post.class,id);
    }

    @Override
    public void delete(Post post) {
        em.remove(post);
    }

    @Override
    public List<File> findAllByPost(Long id) {
        return em.createQuery("select f from File f where f.post.id=:id", File.class)
                .setParameter("id", id)
                .getResultList();
    }
}
