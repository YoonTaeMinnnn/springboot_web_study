package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

             //스프링에서 생성한 entitymanager를 주입을 해주는 어노테이션
    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);   //member 반환 (단건 조회)
    }

    public List<Member> findAll(){
         return em.createQuery("select m from Member m ", Member.class)
                 .getResultList();
    }



    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name= :name",Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
