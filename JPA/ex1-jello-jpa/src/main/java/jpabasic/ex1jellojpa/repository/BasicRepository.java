package jpabasic.ex1jellojpa.repository;

import jpabasic.ex1jellojpa.domain.Locker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BasicRepository {

    private final EntityManager em;

    public List<Locker> joinMember() {
        return em.createQuery("select m.locker from Member m join m.locker", Locker.class)
                .getResultList();
    }

    public List<Locker> outerJoinMember() {
        return em.createQuery("select m.locker from Member m left join m.locker", Locker.class)
                .getResultList();
    }



}
