package login.jwtlogin.repository;

import login.jwtlogin.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class MemberRepository {

    private final EntityManager em;

    @Transactional
    public void save(Member member) {
        em.persist(member);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId= :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<Member> findByNickName(String nickname) {
        return em.createQuery("select m from Member m where m.nickname = :nickname", Member.class)
                .setParameter("nickname", nickname)
                .getResultList()
                .stream().findFirst();
    }



}
