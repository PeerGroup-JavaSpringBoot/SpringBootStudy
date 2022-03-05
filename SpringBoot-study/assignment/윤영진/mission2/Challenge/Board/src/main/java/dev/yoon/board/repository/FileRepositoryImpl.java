package dev.yoon.board.repository;
import dev.yoon.board.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(File file) {
        em.persist(file);
    }

    @Override
    public List<File> findFilebyPostId(Long id) {
        return em.createQuery("select f from File f where f.post.id =:id", File.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void deleteFile(File file) {
        em.remove(file);
    }

    @Override
    public File findFilebyFileId(Long id) {
        return em.find(File.class,id);
    }


}
