package login.jwtlogin.repository;

import login.jwtlogin.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    @Transactional
    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Long id) {
        return em.find(Board.class, id);
    }

}
